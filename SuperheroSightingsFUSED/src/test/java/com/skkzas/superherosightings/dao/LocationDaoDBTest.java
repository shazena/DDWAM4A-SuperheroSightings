package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Organization;
import com.skkzas.superherosightings.dto.Power;
import com.skkzas.superherosightings.dto.Sighting;
import com.skkzas.superherosightings.dto.Superhero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 30, 2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {

    @Autowired
    PowerDao powerDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    public LocationDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePowerById(power.getPowerId());
        }

        List<Superhero> supers = superheroDao.getAllSuperheros();
        for (Superhero superhero : supers) {
            superheroDao.deleteSuperheroById(superhero.getSuperheroId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getOrgId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getSightingId());
        }
    }

    /**
     * Test of addLocation and getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testAddGetLocationById() {
        Location superLocation = new Location();
        superLocation.setLocationName("Super Location");
        superLocation.setDescription("You can't beat it");
        superLocation.setAddress("101 Bedford Ave");
        superLocation.setCity("Brooklyn");
        superLocation.setState("NY");
        superLocation.setZip("11211");
        superLocation.setLatitude("40.720239");
        superLocation.setLongitude("-73.954620");
        superLocation = locationDao.addLocation(superLocation);

        Location fromDao = locationDao.getLocationById(superLocation.getLocationId());

        assertEquals(superLocation, fromDao);

    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location superLocation = new Location();
        superLocation.setLocationName("Super Location");
        superLocation.setDescription("You can't beat it");
        superLocation.setAddress("101 Bedford Ave");
        superLocation.setCity("Brooklyn");
        superLocation.setState("NY");
        superLocation.setZip("11211");
        superLocation.setLatitude("40.720239");
        superLocation.setLongitude("-73.954620");
        superLocation = locationDao.addLocation(superLocation);

        Location superLocation2 = new Location();
        superLocation2.setLocationName("Super Location 2");
        superLocation2.setDescription("You can't beat it either");
        superLocation2.setAddress("102 Bedford Ave");
        superLocation2.setCity("Brooklyn");
        superLocation2.setState("NY");
        superLocation2.setZip("11211");
        superLocation2.setLatitude("40.720440");
        superLocation2.setLongitude("-73.955290");
        superLocation2 = locationDao.addLocation(superLocation2);

        List<Location> allLocations = locationDao.getAllLocations();

        assertEquals(allLocations.size(), 2);
        assertTrue(allLocations.contains(superLocation));
        assertTrue(allLocations.contains(superLocation2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {

        Location superLocation = new Location();
        superLocation.setLocationName("Super Location");
        superLocation.setDescription("You can't beat it");
        superLocation.setAddress("101 Bedford Ave");
        superLocation.setCity("Brooklyn");
        superLocation.setState("NY");
        superLocation.setZip("11211");
        superLocation.setLatitude("40.720239");
        superLocation.setLongitude("-73.954620");
        superLocation = locationDao.addLocation(superLocation);

        Location fromDao = locationDao.getLocationById(superLocation.getLocationId());

        assertEquals(superLocation, fromDao);

        superLocation.setLocationName("Super Location 2");
        superLocation.setDescription("You can't beat it either");
        superLocation.setAddress("102 Bedford Ave");
        superLocation.setCity("Brooklyn");
        superLocation.setState("NY");
        superLocation.setZip("11211");
        superLocation.setLatitude("40.720440");
        superLocation.setLongitude("-73.955290");

        assertNotEquals(superLocation, fromDao);

        locationDao.updateLocation(superLocation);

        fromDao = locationDao.getLocationById(superLocation.getLocationId());

        assertEquals(superLocation, fromDao);

    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {

        Power power = new Power();
        power.setPowerName("listening to people");
        power = powerDao.addPower(power);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Location superLocation = new Location();
        superLocation.setLocationName("Super Location");
        superLocation.setDescription("You can't beat it");
        superLocation.setAddress("101 Bedford Ave");
        superLocation.setCity("Brooklyn");
        superLocation.setState("NY");
        superLocation.setZip("11211");
        superLocation.setLatitude("40.720239");
        superLocation.setLongitude("-73.954620");
        superLocation = locationDao.addLocation(superLocation);

        Sighting firstSighting = new Sighting();
        LocalDate date = LocalDate.parse("2020-01-08");
        firstSighting.setDate(date);
        firstSighting.setLocation(superLocation);
        firstSighting.setSuperhero(mySuper);
        sightingDao.addSighting(firstSighting);

        Organization superOrg = new Organization();
        superOrg.setOrgName("True superheroes");
        superOrg.setDescription("True superheroes are among us");
        superOrg.setPhoneNumber("3475937574");
        superOrg.setLocation(superLocation);
        List<Superhero> supers = new ArrayList<>();
        supers.add(mySuper);
        superOrg.setListOfSuperheroes(supers);
        organizationDao.addOrganization(superOrg);

        Organization superOrg2 = new Organization();
        superOrg2.setOrgName("True superheroes 2");
        superOrg2.setDescription("True superheroes are among us 2");
        superOrg2.setPhoneNumber("3475937511");
        superOrg2.setLocation(superLocation);
        List<Superhero> supers2 = new ArrayList<>();
        supers2.add(mySuper);
        superOrg2.setListOfSuperheroes(supers2);
        organizationDao.addOrganization(superOrg2);

        Location superLocation2 = new Location();
        superLocation2.setLocationName("Super Location 2");
        superLocation2.setDescription("You can't beat it either");
        superLocation2.setAddress("102 Bedford Ave");
        superLocation2.setCity("Brooklyn");
        superLocation2.setState("NY");
        superLocation2.setZip("11211");
        superLocation2.setLatitude("40.720440");
        superLocation2.setLongitude("-73.955290");
        superLocation2 = locationDao.addLocation(superLocation2);

        List<Location> allLocations = locationDao.getAllLocations();

        assertEquals(allLocations.size(), 2);
        assertTrue(allLocations.contains(superLocation));
        assertTrue(allLocations.contains(superLocation2));

        locationDao.deleteLocationById(superLocation.getLocationId());

        allLocations = locationDao.getAllLocations();

        assertEquals(allLocations.size(), 1);
        assertFalse(allLocations.contains(superLocation));
        assertTrue(allLocations.contains(superLocation2));

    }

    /**
     * Test of getAllLocationsForSuperhero method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocationsForSuperhero() {
        Location superLocation = new Location();
        superLocation.setLocationName("Super Location");
        superLocation.setDescription("You can't beat it");
        superLocation.setAddress("101 Bedford Ave");
        superLocation.setCity("Brooklyn");
        superLocation.setState("NY");
        superLocation.setZip("11211");
        superLocation.setLatitude("40.720239");
        superLocation.setLongitude("-73.954620");
        superLocation = locationDao.addLocation(superLocation);

        Location superLocation2 = new Location();
        superLocation2.setLocationName("Super Location 2");
        superLocation2.setDescription("You can't beat it either");
        superLocation2.setAddress("102 Bedford Ave");
        superLocation2.setCity("Brooklyn");
        superLocation2.setState("NY");
        superLocation2.setZip("11211");
        superLocation2.setLatitude("40.720440");
        superLocation2.setLongitude("-73.955290");
        superLocation2 = locationDao.addLocation(superLocation2);

        Location superLocation3 = new Location();
        superLocation3.setLocationName("Super Location 3");
        superLocation3.setDescription("Who's beating things?!?");
        superLocation3.setAddress("103 Bedford Ave");
        superLocation3.setCity("Brooklyn");
        superLocation3.setState("NY");
        superLocation3.setZip("11211");
        superLocation3.setLatitude("40.720074");
        superLocation3.setLongitude("-73.954966");
        superLocation3 = locationDao.addLocation(superLocation3);

        Power power = new Power();
        power.setPowerName("listening to people");
        power = powerDao.addPower(power);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Sighting firstSighting = new Sighting();
        LocalDate date = LocalDate.parse("2020-01-08");
        firstSighting.setDate(date);
        firstSighting.setLocation(superLocation);
        firstSighting.setSuperhero(mySuper);
        sightingDao.addSighting(firstSighting);

        Sighting secondSighting = new Sighting();
        LocalDate date2 = LocalDate.parse("2020-01-09"); //not sure about the format yet
        secondSighting.setDate(date2);
        secondSighting.setLocation(superLocation2); //different location
        secondSighting.setSuperhero(mySuper); //same super
        sightingDao.addSighting(secondSighting);

        List<Location> allLocations = locationDao.getAllLocations();
        assertEquals(allLocations.size(), 3);
        assertTrue(allLocations.contains(superLocation));
        assertTrue(allLocations.contains(superLocation2));
        assertTrue(allLocations.contains(superLocation3));

        List<Location> allLocationsForSuperhero = locationDao.getAllLocationsForSuperhero(mySuper);

        assertEquals(allLocationsForSuperhero.size(), 2);
        assertTrue(allLocationsForSuperhero.contains(superLocation));
        assertTrue(allLocationsForSuperhero.contains(superLocation2));
        assertFalse(allLocationsForSuperhero.contains(superLocation3));

    }

}
