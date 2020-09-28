package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Superhero;
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
public class SuperheroDaoDB {

    @Autowired
    JdbcTemplate jdbc;
    public Superhero getSuperheroById(int id) {
        try {
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM Superhero "
                    + "WHERE SuperheroId = ?";
            return jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<Superhero> getAllSuperheros() {
        final String GET_ALL_SUPERHEROS = "SELECT * FROM Superhero";
        return jdbc.query(GET_ALL_SUPERHEROS, new SuperheroMapper());
    }

    @Transactional
    public Superhero addSuperhero(Superhero Superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO Superhero"
                + "(SuperheroName, Description) "
                + "VALUES(?,?)";

        jdbc.update(INSERT_SUPERHERO,
                Superhero.getSuperheroName(),
                Superhero.getSuperheroDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        Superhero.setSuperheroId(newId);

        return Superhero;
    }

    public void updateSuperhero(Superhero Superhero) {
        final String UPDATE_Superhero = "UPDATE Superhero SET "
                + "SuperheroName = ?, "
                + "Description = ? ";

        jdbc.update(UPDATE_Superhero,
                Superhero.getSuperheroName(),
                Superhero.getSuperheroDescription());
    }

    @Transactional
    public void deleteSuperheroById(int id) {
        try {
            final String DELETE_SIGHTING = "DELETE FROM Sighting "
                    + "WHERE SuperheroId = ?";
            jdbc.update(DELETE_SIGHTING, id);
        } catch (DataAccessException e) {

        }

        try {

            final String GET_ORGANIZATION_FOR_Superhero = "SELECT * FROM Organization "
                    + "WHERE SuperheroId = ?";
            Organization organization = jdbc.queryForObject(GET_ORGANIZATION_FOR_Superhero, new OrganizationDaoDB.OrganizationMapper(), id);

            final String DELETE_SUPERHEROORGANIZATION = "DELETE * FROM SuperheroOrganization "
                    + "WHERE OrgId = ?";
            jdbc.update(DELETE_SUPERHEROORGANIZATION, organization.getOrgId());

            final String DELETE_ORGANIZATION = "DELETE FROM Organization "
                    + "WHERE SuperheroId = ?";
            jdbc.update(DELETE_ORGANIZATION, id);

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
