package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Power;
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
public class PowerDaoDB {

    @Autowired
    JdbcTemplate jdbc;

    public Power getPowerById(int id) {
        try {
            final String GET_POWER_BY_ID = "SELECT * FROM Power "
                    + "WHERE PowerId = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID, new PowerMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<Power> getAllPowers() {
        final String GET_ALL_POWERS = "SELECT * FROM Power";
        return jdbc.query(GET_ALL_POWERS, new PowerMapper());
    }

    @Transactional
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

    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE Power SET "
                + "PowerName = ?, ";

        jdbc.update(UPDATE_POWER,
                power.getPowerName());
    }

    @Transactional
    public void deletePowerById(int id) {
        try {
            final String DELETE_SUPERHEROPOWER = "DELETE FROM SuperheroPower "
                    + "WHERE PowerId = ?";
            jdbc.update(DELETE_SUPERHEROPOWER, id);
        } catch (DataAccessException e) {

        }

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
