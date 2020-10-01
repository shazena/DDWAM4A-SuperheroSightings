package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dao.SuperheroDaoDB.SuperheroMapper;
import com.skkzas.superherosightings.dto.Power;
import com.skkzas.superherosightings.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 25, 2020
 */
@Repository
public class PowerDaoDB implements PowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power getPowerById(int id) {
        try {
            final String GET_POWER_BY_ID = "SELECT * FROM Power "
                    + "WHERE PowerId = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID, new PowerMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        final String GET_ALL_POWERS = "SELECT * FROM Power";
        return jdbc.query(GET_ALL_POWERS, new PowerMapper());
    }

    @Transactional
    @Override
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO Power"
                + "(PowerName) "
                + "VALUES(?)";

        jdbc.update(INSERT_POWER,
                power.getPowerName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);

        return power;
    }

    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE Power SET "
                + "PowerName = ? "
                + "WHERE PowerId = ?";

        jdbc.update(UPDATE_POWER,
                power.getPowerName(),
                power.getPowerId());
    }

    @Transactional
    @Override
    public void deletePowerById(int id) {

        //first find all supers with that power
        final String SELECT_SUPERS_WITH_THIS_POWER = "SELECT * FROM Superhero su "
                + "JOIN Power p ON su.PowerId = p.PowerId "
                + "WHERE su.PowerId = ?";
        List<Superhero> allSuperheroesWithThisPower = jdbc.query(SELECT_SUPERS_WITH_THIS_POWER, new SuperheroMapper(), id);

        //for each super with that power, delete the sighting for that Super and the Organization link in the bridge table
        final String DELETE_SUPERHERO_FROM_SUPERORGANIZATION = "DELETE FROM SuperheroOrganization "
                + "WHERE SuperheroId = ?";
        final String DELETE_SIGHTING = "DELETE FROM Sighting "
                + "WHERE SuperheroId = ?";

        for (Superhero superhero : allSuperheroesWithThisPower) {
            jdbc.update(DELETE_SUPERHERO_FROM_SUPERORGANIZATION, superhero.getSuperheroId());
            jdbc.update(DELETE_SIGHTING, superhero.getSuperheroId());
        }

        //now delete all those superheroes with that powerId
        final String DELETE_SUPERHEROES = "DELETE FROM Superhero "
                + "WHERE PowerId = ?";
        jdbc.update(DELETE_SUPERHEROES, id);

        //finally, delete the power, you have deleted all dependencies on it
        final String DELETE_POWER = "DELETE FROM Power "
                + "WHERE PowerId = ?";
        jdbc.update(DELETE_POWER, id);

    }

    public static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            Power power = new Power();
            power.setPowerId(rs.getInt("PowerId"));
            power.setPowerName(rs.getString("PowerName"));

            return power;
        }
    }
}
