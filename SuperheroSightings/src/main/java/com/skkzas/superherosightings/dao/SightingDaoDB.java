package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dao.LocationDaoDB.LocationMapper;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Sighting;
import com.skkzas.superherosightings.dto.Superhero;
import static java.lang.Integer.min;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 25, 2020
 */
@Repository
public class SightingDaoDB {

    //method to get last 10 sightings, organized by date
    @Autowired
    JdbcTemplate jdbc;
//    private int sightingId;
//    private LocalDate date;
//    private Location location;
//    private Superhero superhero;
    public Sighting getSightingById(int id) {

        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM Sighting "
                    + "WHERE SightingId = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);

            sighting.setLocation(getLocationForSighting(id));
            sighting.setSuperhero(getSuperheroForSighting(id));

            return sighting;
        } catch (DataAccessException e) {
            return null;
        }

    }

    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndSuperhero(sightings);
        return sightings;
    }

    public List<Sighting> getLastTenSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndSuperhero(sightings);
        sightings.sort(Comparator.comparing(Sighting::getDate));
        sightings = sightings.subList(0, min(sightings.size(), 11));
        return sightings;
    }

    @Transactional
    public Sighting addSighting(Sighting location) {
        //NEEDS IMPLEMENTATION
        return location;
    }

    public void updateSighting(Sighting location) {
        //NEEDS IMPLEMENTATION

    }

    public void deleteSightingById(int id) {
        //NEEDS IMPLEMENTATION

    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT * FROM Location "
                + "WHERE SightingId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT * FROM Superhero "
                + "WHERE SightingId = ?";
        return jdbc.queryForObject(SELECT_SUPERHERO_FOR_SIGHTING, new SuperheroMapper(), id);
    }

    private void associateLocationAndSuperhero(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
            sighting.setSuperhero(getSuperheroForSighting(sighting.getSightingId()));
        }
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("SightingId"));
            sighting.setDate(rs.getDate("Date").toLocalDate());

            return sighting;
        }
    }
}
