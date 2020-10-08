package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dao.LocationDaoDB.LocationMapper;
import com.skkzas.superherosightings.dao.PowerDaoDB.PowerMapper;
import com.skkzas.superherosightings.dao.SuperheroDaoDB.SuperheroMapper;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Power;
import com.skkzas.superherosightings.dto.Sighting;
import com.skkzas.superherosightings.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.min;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 25, 2020
 */
@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
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

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndSuperhero(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getLastTenSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndSuperhero(sightings);
        sightings.sort(Comparator.comparing(Sighting::getDate));
        Collections.reverse(sightings);
        List<Sighting> lastTenSightings = new ArrayList<>(sightings.subList(0, min(sightings.size(), 10)));
        return lastTenSightings;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        //to add a sighting, you need a location and a superhero in the db already

        final String INSERT_SIGHTING = "INSERT INTO Sighting(date,locationId, superheroId) VALUES "
                + "(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getLocationId(),
                sighting.getSuperhero().getSuperheroId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);

        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {

        final String UPDATE_SIGHTING = "UPDATE Sighting SET "
                + "date = ?, "
                + "locationId = ?, "
                + "superheroId = ? "
                + "WHERE SightingId = ?";

        jdbc.update(UPDATE_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getLocationId(),
                sighting.getSuperhero().getSuperheroId(),
                sighting.getSightingId());

    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting "
                + "WHERE SightingID = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getAllSightingsForDate(LocalDate date) {
        final String GET_SIGHTINGS_FOR_DATE = "SELECT * FROM Sighting si "
                + "INNER JOIN superhero su ON si.superheroId = su.superheroId "
                + "WHERE si.date = ?";

        List<Sighting> sightingsForDate = jdbc.query(GET_SIGHTINGS_FOR_DATE, new SightingMapper(), date);
        associateLocationAndSuperhero(sightingsForDate);
        return sightingsForDate;
    }

    @Override
    public List<Sighting> getAllSightingsForListOfSuperheros(List<Superhero> listOfSuperheroes) {
        List <Sighting> allSightingsForSuperheroes = new ArrayList<>();
        final String GET_SIGHTINGS_FOR_SUPERHERO = "SELECT * FROM Sighting WHERE SuperheroId = ?";

        for (Superhero superhero : listOfSuperheroes) {
           List<Sighting> sightingsForSuperhero = jdbc.query(GET_SIGHTINGS_FOR_SUPERHERO, new SightingMapper(), superhero.getSuperheroId());
            for (Sighting sighting : sightingsForSuperhero) {
                allSightingsForSuperheroes.add(sighting);
            }
        }
        associateLocationAndSuperhero(allSightingsForSuperheroes);
        return allSightingsForSuperheroes;
    }

    @Override
    public List<Sighting> getAllSightingsForLocation(Location location) {
        final String GET_SIGHTINGS_FOR_LOCATION = "SELECT * FROM Sighting WHERE LocationId = ?";

        List<Sighting> sightingsForLocation = jdbc.query(GET_SIGHTINGS_FOR_LOCATION, new SightingMapper(), location.getLocationId());
        associateLocationAndSuperhero(sightingsForLocation);
        return sightingsForLocation;
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT * FROM Location l "
                + "JOIN Sighting s ON l.LocationId = s.LocationId "
                + "WHERE SightingId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT * FROM Superhero su "
                + "JOIN Sighting si ON su.SuperheroId = si.SuperheroId "
                + "WHERE SightingId = ?";
        Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_FOR_SIGHTING, new SuperheroMapper(), id);

        final String SELECT_POWER_FOR_SUPER = "SELECT p.PowerId, p.PowerName FROM Power p "
                + "JOIN Superhero s ON s.PowerId = p.PowerId WHERE SuperheroId = ?";

        Power power = jdbc.queryForObject(SELECT_POWER_FOR_SUPER, new PowerMapper(), superhero.getSuperheroId());

        superhero.setPower(power);

        return superhero;

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
