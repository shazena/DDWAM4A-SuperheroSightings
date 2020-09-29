package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Organization;
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
public class SuperheroDaoDB implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM Superhero "
                    + "WHERE SuperheroId = ?";
            return jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Superhero> getAllSuperheros() {
        final String GET_ALL_SUPERHEROS = "SELECT * FROM Superhero";
        return jdbc.query(GET_ALL_SUPERHEROS, new SuperheroMapper());
    }

    @Transactional
    @Override
    public Superhero addSuperhero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO Superhero"
                + "(SuperheroName, Description) "
                + "VALUES(?,?)";

        jdbc.update(INSERT_SUPERHERO,
                superhero.getSuperheroName(),
                superhero.getSuperheroDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setSuperheroId(newId);

        /**
         * TODO Maybe in front-end, on the add Super page, you can allow the
         * user to choose an existing or add a new power then you would have to
         * process that first in the service and then add it to the powerDao if
         * necessary, then add the power to the superhero, then add the super
         * here.*
         */
        return superhero;
    }

    @Override
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_Superhero = "UPDATE Superhero SET "
                + "SuperheroName = ?, "
                + "Description = ? "
                + "WHERE SuperheroId = ?";

        jdbc.update(UPDATE_Superhero,
                superhero.getSuperheroName(),
                superhero.getSuperheroDescription(),
                superhero.getSuperheroId());
    }

    @Transactional
    @Override
    public void deleteSuperheroById(int id) {
        try {
            final String DELETE_SIGHTING = "DELETE FROM Sighting "
                    + "WHERE SuperheroId = ?";
            jdbc.update(DELETE_SIGHTING, id);
        } catch (DataAccessException e) {

        }

        try {

            //get all organizations
            
            
            final String DELETE_SUPERHEROORGANIZATION = "DELETE * FROM SuperheroOrganization "
                    + "WHERE SuperheroId = ?";
            jdbc.update(DELETE_SUPERHEROORGANIZATION, id);

//            final String DELETE_ORGANIZATION = "DELETE FROM Organization "
//                    + "WHERE SuperheroId = ?";
//            jdbc.update(DELETE_ORGANIZATION, id);

        } catch (DataAccessException e) {

        }

        try {
            final String DELETE_SUPERHEROPOWER = "DELETE FROM SuperheroPower "
                    + "WHERE SuperheroId = ?";
            jdbc.update(DELETE_SUPERHEROPOWER, id);
        } catch (DataAccessException e) {

        }

        final String DELETE_Superhero = "DELETE FROM Superhero "
                + "WHERE SuperheroId = ?";
        jdbc.update(DELETE_Superhero, id);

    }

    @Override
    public List<Superhero> getAllSuperheroesForLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Superhero> getAllSuperherosForOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setSuperheroId(rs.getInt("SuperheroId"));
            superhero.setSuperheroName(rs.getString("SuperheroName"));
            superhero.setSuperheroDescription(rs.getString("Description"));

            return superhero;
        }
    }
}
