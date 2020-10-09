package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dao.LocationDaoDB.LocationMapper;
import com.skkzas.superherosightings.dao.SuperheroDaoDB.SuperheroMapper;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String GET_ORGANIZATION = "SELECT * FROM Organization "
                    + "WHERE orgId = ?";
            Organization organization = jdbc.queryForObject(GET_ORGANIZATION, new OrganizationMapper(), id);
            organization.setLocation(getLocationForOrganization(id));
            organization.setListOfSuperheroes(getSuperheroesForOrganization(id));
            return organization;
        } catch (DataAccessException e) {
            return null;
        }

    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATIONS = "SELECT * FROM Organization";
        List<Organization> allOrganizations = jdbc.query(GET_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateLocationAndSuperheroes(allOrganizations);
        return allOrganizations;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO Organization"
                + "(OrgName, Description, PhoneNumber, LocationId) VALUES (?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getOrgName(),
                organization.getDescription(),
                organization.getPhoneNumber(),
                organization.getLocation().getLocationId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrgId(newId);

        insertSuperheroOrganization(organization);

        return organization;

    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE Organization SET "
                + "OrgName = ?, "
                + "Description = ?, "
                + "PhoneNumber = ?, "
                + "LocationId = ? "
                + "WHERE OrgId = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getOrgName(),
                organization.getDescription(),
                organization.getPhoneNumber(),
                organization.getLocation().getLocationId(),
                organization.getOrgId());

        final String DELETE_SUPERHEROORGANIZATION = "DELETE FROM SuperheroOrganization "
                + "WHERE OrgId = ?";
        jdbc.update(DELETE_SUPERHEROORGANIZATION, organization.getOrgId());

        insertSuperheroOrganization(organization);

    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {

        final String DELETE_SUPERHEROORGANIZATION = "DELETE FROM SuperheroOrganization "
                + "WHERE OrgId = ?";
        jdbc.update(DELETE_SUPERHEROORGANIZATION, id);

        final String DELETE_ORGANIZATION = "DELETE FROM Organization WHERE OrgId = ?";
        jdbc.update(DELETE_ORGANIZATION, id);

    }

    @Override
    public List<Organization> getOrganizationsForSuperhero(Superhero superhero) {
        final String GET_ORGANIZATIONS_FOR_SUPERHERO = "SELECT * FROM Organization o "
                + "JOIN SuperheroOrganization so ON so.OrgId = o.OrgId "
                + "JOIN Superhero su ON su.SuperheroId = so.SuperheroId "
                + "WHERE su.SuperheroId = ?";
        List<Organization> organizationsForSuperhero = jdbc.query(GET_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationMapper(), superhero.getSuperheroId());

        associateLocationAndSuperheroes(organizationsForSuperhero);

        return organizationsForSuperhero;
    }

    @Override
    public List<Organization> getOrganizationsForLocation(Location location) {
        final String GET_ORGANIZATIONS_FOR_LOCATION = "SELECT * FROM Organization o " +
                "JOIN Location l ON o.LocationId = l.LocationId " +
                "WHERE l.LocationId = ?";

        List<Organization> organizationsForLocation = jdbc.query(GET_ORGANIZATIONS_FOR_LOCATION, new OrganizationMapper(), location.getLocationId());

        associateLocationAndSuperheroes(organizationsForLocation);

        return organizationsForLocation;
    }

    private Location getLocationForOrganization(int orgId) {
        final String GET_LOCATION_FOR_ORGANIZATION = "SELECT * FROM Location l "
                + "JOIN Organization o ON l.LocationId = o.LocationId "
                + "WHERE o.OrgId = ?";
        return jdbc.queryForObject(GET_LOCATION_FOR_ORGANIZATION, new LocationMapper(), orgId);
    }

    private List<Superhero> getSuperheroesForOrganization(int orgId) {

        final String GET_SUPERHEROES_FOR_ORGANIZATION = "SELECT * FROM Superhero su "
                + "JOIN SuperheroOrganization so ON su.SuperheroId = so.SuperheroId "
                + "JOIN Organization o ON o.OrgId = so.OrgId "
                + "WHERE o.orgId = ?";

        List<Superhero> superheroesForOrganization = jdbc.query(GET_SUPERHEROES_FOR_ORGANIZATION, new SuperheroMapper(), orgId);
        for (Superhero superhero : superheroesForOrganization) {
            final String SELECT_POWER_FOR_SUPER = "SELECT p.PowerId, p.PowerName FROM Power p "
                    + "JOIN Superhero s ON s.PowerId = p.PowerId WHERE SuperheroId = ?";

            Power powerForSuperhero = jdbc.queryForObject(SELECT_POWER_FOR_SUPER, new PowerDaoDB.PowerMapper(), superhero.getSuperheroId());
            superhero.setPower(powerForSuperhero);
        }

        return superheroesForOrganization;

    }

    private void associateLocationAndSuperheroes(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setLocation(getLocationForOrganization(organization.getOrgId()));
            organization.setListOfSuperheroes(getSuperheroesForOrganization(organization.getOrgId()));
        }
    }

    private void insertSuperheroOrganization(Organization organization) {
        for (Superhero superhero : organization.getListOfSuperheroes()) {
            final String INSERT_SUPERHEROORGANIZATION = "INSERT INTO SuperheroOrganization"
                    + "(SuperheroId, OrgId) VALUES (?,?)";
            jdbc.update(INSERT_SUPERHEROORGANIZATION,
                    superhero.getSuperheroId(),
                    organization.getOrgId());
        }
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrgId(rs.getInt("OrgId"));
            organization.setOrgName(rs.getString("OrgName"));
            organization.setDescription(rs.getString("Description"));
            organization.setPhoneNumber(rs.getString("PhoneNumber"));

            return organization;
        }
    }
}
