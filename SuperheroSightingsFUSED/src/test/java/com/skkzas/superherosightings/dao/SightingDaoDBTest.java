package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 30, 2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoDBTest {

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

    public SightingDaoDBTest() {
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
     * Test of addSighting and getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testAddGetSightingById() {

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
        Sighting addedSighting = sightingDao.addSighting(firstSighting);

        Sighting fromDao = sightingDao.getSightingById(addedSighting.getSightingId());

        assertEquals(firstSighting, fromDao);

    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {

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
        Sighting addedSighting = sightingDao.addSighting(firstSighting);

        Sighting fromDao = sightingDao.getSightingById(addedSighting.getSightingId());

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

        Sighting secondSighting = new Sighting();
        LocalDate date2 = LocalDate.parse("2020-01-09"); //not sure about the format yet
        secondSighting.setDate(date2);
        secondSighting.setLocation(superLocation2); //different location
        secondSighting.setSuperhero(mySuper); //same super
        Sighting addedSighting2 = sightingDao.addSighting(secondSighting);

        Sighting fromDao2 = sightingDao.getSightingById(addedSighting2.getSightingId());

        List<Sighting> allSightings = sightingDao.getAllSightings();

        assertEquals(allSightings.size(), 2);
        assertTrue(allSightings.contains(fromDao));
        assertTrue(allSightings.contains(fromDao2));

    }

    /**
     * Test of getLastTenSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetLastTenSightings() {

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

        LocalDate date = LocalDate.parse("2020-01-08");
        LocalDate date2 = LocalDate.parse("2020-01-10");
        LocalDate date3 = LocalDate.parse("2020-01-09");
        LocalDate date4 = LocalDate.parse("2020-01-12");
        LocalDate date5 = LocalDate.parse("2020-01-11");
        LocalDate date6 = LocalDate.parse("2020-01-13");
        LocalDate date7 = LocalDate.parse("2020-01-14");
        LocalDate date8 = LocalDate.parse("2020-01-15");
        LocalDate date9 = LocalDate.parse("2020-01-17");
        LocalDate date10 = LocalDate.parse("2020-01-16");
        LocalDate date11 = LocalDate.parse("2020-01-18");

        Sighting firstSighting = new Sighting();
        firstSighting.setDate(date);
        firstSighting.setLocation(superLocation);
        firstSighting.setSuperhero(mySuper);
        firstSighting = sightingDao.addSighting(firstSighting);

        Sighting secondSighting = new Sighting();
        secondSighting.setDate(date2);
        secondSighting.setLocation(superLocation);
        secondSighting.setSuperhero(mySuper);
        sightingDao.addSighting(secondSighting);

        Sighting thirdSighting = new Sighting();
        thirdSighting.setDate(date3);
        thirdSighting.setLocation(superLocation);
        thirdSighting.setSuperhero(mySuper);
        sightingDao.addSighting(thirdSighting);

        Sighting fourthSighting = new Sighting();
        fourthSighting.setDate(date4);
        fourthSighting.setLocation(superLocation);
        fourthSighting.setSuperhero(mySuper);
        sightingDao.addSighting(fourthSighting);

        Sighting fifthSighting = new Sighting();
        fifthSighting.setDate(date5);
        fifthSighting.setLocation(superLocation);
        fifthSighting.setSuperhero(mySuper);
        sightingDao.addSighting(fifthSighting);

        Sighting sixthSighting = new Sighting();
        sixthSighting.setDate(date6);
        sixthSighting.setLocation(superLocation);
        sixthSighting.setSuperhero(mySuper);
        sightingDao.addSighting(sixthSighting);

        Sighting seventhSighting = new Sighting();
        seventhSighting.setDate(date7);
        seventhSighting.setLocation(superLocation);
        seventhSighting.setSuperhero(mySuper);
        sightingDao.addSighting(seventhSighting);

        Sighting eighthSighting = new Sighting();
        eighthSighting.setDate(date8);
        eighthSighting.setLocation(superLocation);
        eighthSighting.setSuperhero(mySuper);
        sightingDao.addSighting(eighthSighting);

        Sighting ninthSighting = new Sighting();
        ninthSighting.setDate(date9);
        ninthSighting.setLocation(superLocation);
        ninthSighting.setSuperhero(mySuper);
        sightingDao.addSighting(ninthSighting);

        Sighting tenthSighting = new Sighting();
        tenthSighting.setDate(date10);
        tenthSighting.setLocation(superLocation);
        tenthSighting.setSuperhero(mySuper);
        sightingDao.addSighting(tenthSighting);

        Sighting eleventhSighting = new Sighting();
        eleventhSighting.setDate(date11);
        eleventhSighting.setLocation(superLocation);
        eleventhSighting.setSuperhero(mySuper);
        sightingDao.addSighting(eleventhSighting);

        List<Sighting> allSightings = sightingDao.getAllSightings();
        assertEquals(allSightings.size(), 11);

        List<Sighting> lastTenSightings = sightingDao.getLastTenSightings();

        assertEquals(lastTenSightings.size(), 10);

        //check to see that the 11th oldest is not in the list
        assertFalse(lastTenSightings.contains(firstSighting));

        //check if dates are in correct order
        assertEquals(lastTenSightings.get(0).getDate(), date11);
        assertEquals(lastTenSightings.get(1).getDate(), date9);
        assertEquals(lastTenSightings.get(2).getDate(), date10);
        assertEquals(lastTenSightings.get(3).getDate(), date8);
        assertEquals(lastTenSightings.get(4).getDate(), date7);
        assertEquals(lastTenSightings.get(5).getDate(), date6);
        assertEquals(lastTenSightings.get(6).getDate(), date4);
        assertEquals(lastTenSightings.get(7).getDate(), date5);
        assertEquals(lastTenSightings.get(8).getDate(), date2);
        assertEquals(lastTenSightings.get(9).getDate(), date3);
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {

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
        Sighting addedSighting = sightingDao.addSighting(firstSighting);

        Sighting fromDao = sightingDao.getSightingById(addedSighting.getSightingId());

        assertEquals(firstSighting, fromDao);

        Power power2 = new Power();
        power2.setPowerName("listening to people");
        power2 = powerDao.addPower(power2);

        Superhero mySuper2 = new Superhero();
        mySuper2.setSuperheroName("Another great superhero");
        mySuper2.setSuperheroDescription("doesn't judge");
        mySuper2.setPower(power2);
        superheroDao.addSuperhero(mySuper2);

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

        LocalDate date2 = LocalDate.parse("2020-01-09");
        firstSighting.setDate(date2);
        firstSighting.setLocation(superLocation2);
        firstSighting.setSuperhero(mySuper2);

        assertNotEquals(firstSighting, fromDao);

        sightingDao.updateSighting(firstSighting);

        Sighting fromDao2 = sightingDao.getSightingById(firstSighting.getSightingId());

        assertEquals(firstSighting, fromDao2);

        assertNotEquals(firstSighting, fromDao);

    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
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
        Sighting addedSighting = sightingDao.addSighting(firstSighting);

        Sighting fromDao = sightingDao.getSightingById(addedSighting.getSightingId());

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

        Sighting secondSighting = new Sighting();
        LocalDate date2 = LocalDate.parse("2020-01-09"); //not sure about the format yet
        secondSighting.setDate(date2);
        secondSighting.setLocation(superLocation2); //different location
        secondSighting.setSuperhero(mySuper); //same super
        Sighting addedSighting2 = sightingDao.addSighting(secondSighting);

        Sighting fromDao2 = sightingDao.getSightingById(addedSighting2.getSightingId());

        List<Sighting> allSightings = sightingDao.getAllSightings();

        assertEquals(allSightings.size(), 2);
        assertTrue(allSightings.contains(fromDao));
        assertTrue(allSightings.contains(fromDao2));

        sightingDao.deleteSightingById(fromDao.getSightingId());

        allSightings = sightingDao.getAllSightings();
        assertEquals(allSightings.size(), 1);
        assertFalse(allSightings.contains(fromDao));
        assertTrue(allSightings.contains(fromDao2));

    }

    /**
     * Test of getAllSightingsForDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightingsForDate() {

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
        Sighting addedSighting = sightingDao.addSighting(firstSighting);

        Sighting fromDao = sightingDao.getSightingById(addedSighting.getSightingId());

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

        Sighting secondSighting = new Sighting();
        LocalDate date2 = LocalDate.parse("2020-01-09");
        secondSighting.setDate(date2);
        secondSighting.setLocation(superLocation2);
        secondSighting.setSuperhero(mySuper);
        Sighting addedSighting2 = sightingDao.addSighting(secondSighting);

        Sighting fromDao2 = sightingDao.getSightingById(addedSighting2.getSightingId());

        List<Sighting> allSightings = sightingDao.getAllSightingsForDate(date);

        assertEquals(allSightings.size(), 1);
        assertTrue(allSightings.contains(fromDao));
        assertFalse(allSightings.contains(fromDao2));

    }

    /**
     * Test of getAllSightingsForListOfSuperheros method, of class SightingDaoDB.
     */
    @Test
    public void TestGetAllSightingsForListOfSuperheros() {
        //create 3 supers
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerName("listening to people");
        power2 = powerDao.addPower(power2);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Superhero mySuper2 = new Superhero();
        mySuper2.setSuperheroName("Another great superhero");
        mySuper2.setSuperheroDescription("doesn't judge");
        mySuper2.setPower(power);
        superheroDao.addSuperhero(mySuper2);

        Superhero mySuper3 = new Superhero();
        mySuper3.setSuperheroName("Third superhero guy");
        mySuper3.setSuperheroDescription("sometimes disappears");
        mySuper3.setPower(power2);
        superheroDao.addSuperhero(mySuper3);

        //create 3 sightings
        //first for mySuper
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
        Sighting addedSighting = sightingDao.addSighting(firstSighting);

        //second - for mySuper2
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

        Sighting secondSighting = new Sighting();
        LocalDate date2 = LocalDate.parse("2020-01-09");
        secondSighting.setDate(date2);
        secondSighting.setLocation(superLocation2);
        secondSighting.setSuperhero(mySuper2);
        Sighting addedSighting2 = sightingDao.addSighting(secondSighting);

        //third for mySuper3 (same location as first one)
        Sighting thirdSighting = new Sighting();
        LocalDate date3 = LocalDate.parse("2020-01-09");
        thirdSighting.setDate(date3);
        thirdSighting.setLocation(superLocation);
        thirdSighting.setSuperhero(mySuper3);
        Sighting addedSighting3 = sightingDao.addSighting(thirdSighting);

        //act - pass a list of 2 supers and make sure the method returns 2 sightings for them
        //create a list of Supers containing 2 supers
        List<Superhero> listOfSupers = new ArrayList<>();
        listOfSupers.add(mySuper);
        listOfSupers.add(mySuper2);

        List<Sighting> listOfSightingsForSuperheroes = sightingDao.getAllSightingsForListOfSuperheros(listOfSupers);
        assertEquals(listOfSightingsForSuperheroes.size(), 2);
        assertTrue(listOfSightingsForSuperheroes.contains(addedSighting));
        assertTrue(listOfSightingsForSuperheroes.contains(addedSighting2));
        assertFalse(listOfSightingsForSuperheroes.contains(addedSighting3));
    }

    /**
     * Test of getAllSightingsForLocation method, of class SightingDaoDB.
     */
    @Test
    public void TestGetAllSightingsForLocations() {
        //create 3 sightings, 2 for the same location
        //create 3 supers
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerName("listening to people");
        power2 = powerDao.addPower(power2);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Superhero mySuper2 = new Superhero();
        mySuper2.setSuperheroName("Another great superhero");
        mySuper2.setSuperheroDescription("doesn't judge");
        mySuper2.setPower(power);
        superheroDao.addSuperhero(mySuper2);

        Superhero mySuper3 = new Superhero();
        mySuper3.setSuperheroName("Third superhero guy");
        mySuper3.setSuperheroDescription("sometimes disappears");
        mySuper3.setPower(power2);
        superheroDao.addSuperhero(mySuper3);

        //create 3 sightings
        //first for mySuper
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
        Sighting addedSighting = sightingDao.addSighting(firstSighting);

        //second - for mySuper2
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

        Sighting secondSighting = new Sighting();
        LocalDate date2 = LocalDate.parse("2020-01-09");
        secondSighting.setDate(date2);
        secondSighting.setLocation(superLocation2);
        secondSighting.setSuperhero(mySuper2);
        Sighting addedSighting2 = sightingDao.addSighting(secondSighting);

        //third for mySuper3 (same location as first one)
        Sighting thirdSighting = new Sighting();
        LocalDate date3 = LocalDate.parse("2020-01-09");
        thirdSighting.setDate(date3);
        thirdSighting.setLocation(superLocation);
        thirdSighting.setSuperhero(mySuper3);
        Sighting addedSighting3 = sightingDao.addSighting(thirdSighting);

        //check if the list of sightings for the same location contains 2
        List<Sighting> sightingsForLocation = sightingDao.getAllSightingsForLocation(superLocation);
        assertEquals(sightingsForLocation.size(), 2);
        assertTrue(sightingsForLocation.contains(addedSighting));
        assertTrue(sightingsForLocation.contains(addedSighting3));
        assertFalse(sightingsForLocation.contains(addedSighting2));
    }
}
