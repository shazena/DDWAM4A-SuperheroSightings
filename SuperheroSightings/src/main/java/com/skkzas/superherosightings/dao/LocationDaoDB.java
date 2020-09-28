package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dao.OrganizationDaoDB.OrganizationMapper;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Sep 25, 2020
 */
@Repository
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM Location "
                    + "WHERE LocationId = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM Location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO Location"
                + "(LocationName, Description, Address, City, State, Zip, Latitude, Longitude) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        jdbc.update(INSERT_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);

        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET "
                + "LocationName = ?, "
                + "Description = ?, "
                + "Address = ?, "
                + "City = ?, "
                + "State = ?, "
                + "Zip = ?, "
                + "Latitude = ?, "
                + "Longitude = ? "
                + "WHERE LocationId = ?";

        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    @Transactional
    public void deleteLocationById(int id) {
        try {
            final String DELETE_SIGHTING = "DELETE FROM Sighting "
                    + "WHERE LocationId = ?";
            jdbc.update(DELETE_SIGHTING, id);
        } catch (DataAccessException e) {

        }

        try {

            final String GET_ORGANIZATION_FOR_LOCATION = "SELECT * FROM Organization "
                    + "WHERE LocationId = ?";
            Organization organization = jdbc.queryForObject(GET_ORGANIZATION_FOR_LOCATION, new OrganizationMapper(), id);

            final String DELETE_SUPERHEROORGANIZATION = "DELETE * FROM SuperheroOrganization "
                    + "WHERE OrgId = ?";
            jdbc.update(DELETE_SUPERHEROORGANIZATION, organization.getOrgId());

            final String DELETE_ORGANIZATION = "DELETE FROM Organization "
                    + "WHERE LocationId = ?";
            jdbc.update(DELETE_ORGANIZATION, id);

        } catch (DataAccessException e) {

        }

        final String DELETE_LOCATION = "DELETE FROM Location "
                + "WHERE LocationId = ?";
        jdbc.update(DELETE_LOCATION, id);

    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("LocationId"));
            location.setLocationName(rs.getString("LocationName"));
            location.setDescription(rs.getString("Description"));
            location.setAddress(rs.getString("Address"));
            location.setCity(rs.getString("City"));
            location.setState(rs.getString("State"));
            location.setZip(rs.getString("Zip"));
            location.setLatitude(rs.getString("Latitude"));
            location.setLongitude(rs.getString("Longitude"));

            return location;
        }
    }
}
