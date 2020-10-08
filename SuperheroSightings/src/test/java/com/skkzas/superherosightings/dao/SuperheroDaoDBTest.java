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
 * Date Created: Sep 29, 2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperheroDaoDBTest {

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

    public SuperheroDaoDBTest() {
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
     * Test of getSuperheroById and addSuperhero methods, of class
     * SuperheroDaoDB.
     */
    @Test
    public void testAddAndGetSuperhero() {
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Superhero fromDao = superheroDao.getSuperheroById(mySuper.getSuperheroId());
        assertEquals(mySuper, fromDao);
    }

    /**
     * Test of getAllSuperheros method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetAllSuperheros() {
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Power power2 = new Power();
        power2.setPowerName("listening to people");
        power2 = powerDao.addPower(power2);

        Superhero mySuper2 = new Superhero();
        mySuper2.setSuperheroName("Another great superhero");
        mySuper2.setSuperheroDescription("doesn't judge");
        mySuper2.setPower(power2);
        superheroDao.addSuperhero(mySuper2);

        List<Superhero> supers = superheroDao.getAllSuperheros();

        assertEquals(2, supers.size());
        assertTrue(supers.contains(mySuper));
        assertTrue(supers.contains(mySuper2));
    }

    /**
     * Test of updateSuperhero method, of class SuperheroDaoDB.
     */
    @Test
    public void testUpdateSuperhero() {
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Superhero fromDao = superheroDao.getSuperheroById(mySuper.getSuperheroId());
        assertEquals(mySuper, fromDao);

        mySuper.setSuperheroName("new great name for this guy");
        superheroDao.updateSuperhero(mySuper);

        assertNotEquals(mySuper, fromDao);

        fromDao = superheroDao.getSuperheroById(mySuper.getSuperheroId());

        assertEquals(mySuper, fromDao);

    }

    /**
     * Test of deleteSuperheroById method, of class SuperheroDaoDB.
     */
    @Test
    public void testDeleteSuperheroById() {
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
        locationDao.addLocation(superLocation);

        Organization superOrg = new Organization();
        superOrg.setOrgName("True superheroes");
        superOrg.setDescription("True superheroes are among us");
        superOrg.setPhoneNumber("3475937574");
        superOrg.setLocation(superLocation);
        ArrayList<Superhero> supers = new ArrayList<>();
        supers.add(mySuper);
        superOrg.setListOfSuperheroes(supers);
        organizationDao.addOrganization(superOrg);

        Sighting firstSighting = new Sighting();
        LocalDate date = LocalDate.parse("2020-01-08"); //not sure about the format yet
        firstSighting.setDate(date);
        firstSighting.setLocation(superLocation);
        firstSighting.setSuperhero(mySuper);
        sightingDao.addSighting(firstSighting);

        Superhero fromDao = superheroDao.getSuperheroById(mySuper.getSuperheroId());
        assertEquals(mySuper, fromDao);

        superheroDao.deleteSuperheroById(mySuper.getSuperheroId());

        fromDao = superheroDao.getSuperheroById(mySuper.getSuperheroId());
        assertNull(fromDao);
    }

    /**
     * Test of getAllSuperheroesForLocation method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetAllSuperheroesForLocation() {
        //creating 3 supers
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Power power2 = new Power();
        power2.setPowerName("listening to people");
        power2 = powerDao.addPower(power2);

        Superhero mySuper2 = new Superhero();
        mySuper2.setSuperheroName("Another great superhero");
        mySuper2.setSuperheroDescription("doesn't judge");
        mySuper2.setPower(power2);
        superheroDao.addSuperhero(mySuper2);

        Power power3 = new Power();
        power3.setPowerName("being invisible");
        power3 = powerDao.addPower(power3);

        Superhero mySuper3 = new Superhero();
        mySuper3.setSuperheroName("Third superhero guy");
        mySuper3.setSuperheroDescription("sometimes disappears");
        mySuper3.setPower(power3);
        superheroDao.addSuperhero(mySuper3);

        //checking if all 3 supers were added to DB
        List<Superhero> supers = superheroDao.getAllSuperheros();
        assertEquals(3, supers.size());
        assertTrue(supers.contains(mySuper));
        assertTrue(supers.contains(mySuper2));
        assertTrue(supers.contains(mySuper3));

        //creating a location and adding it to a sighting
        Location superLocation = new Location();
        superLocation.setLocationName("Super Location");
        superLocation.setDescription("You can't beat it");
        superLocation.setAddress("101 Bedford Ave");
        superLocation.setCity("Brooklyn");
        superLocation.setState("NY");
        superLocation.setZip("11211");
        superLocation.setLatitude("40.720239");
        superLocation.setLongitude("-73.954620");
        locationDao.addLocation(superLocation);

        //first sighting
        Sighting firstSighting = new Sighting();
        LocalDate date = LocalDate.parse("2020-01-08"); //not sure about the format yet
        firstSighting.setDate(date);
        firstSighting.setLocation(superLocation);
        firstSighting.setSuperhero(mySuper);
        sightingDao.addSighting(firstSighting);

        //second sighting (with the same location but different super)
        Sighting secondSighting = new Sighting();
        LocalDate date2 = LocalDate.parse("2020-01-09"); //not sure about the format yet
        secondSighting.setDate(date2);
        secondSighting.setLocation(superLocation); //same location
        secondSighting.setSuperhero(mySuper2); //different super
        sightingDao.addSighting(secondSighting);

        //third sighting (different location)
        Location superLocation2 = new Location();
        superLocation2.setLocationName("Super Location 2");
        superLocation2.setDescription("You can't beat it either");
        superLocation2.setAddress("102 Bedford Ave");
        superLocation2.setCity("Brooklyn");
        superLocation2.setState("NY");
        superLocation2.setZip("11211");
        superLocation2.setLatitude("40.720440");
        superLocation2.setLongitude("-73.955290");
        locationDao.addLocation(superLocation2);

        Sighting thirdSighting = new Sighting();
        LocalDate date3 = LocalDate.parse("2020-01-10"); //not sure about the format yet
        thirdSighting.setDate(date3);
        thirdSighting.setLocation(superLocation2); //different location
        thirdSighting.setSuperhero(mySuper3);
        sightingDao.addSighting(thirdSighting);

        //checking if db contains 2 supers for the same location
        List<Superhero> supersForLocation = superheroDao.getAllSuperheroesForLocation(superLocation);

        assertEquals(2, supersForLocation.size());
        assertTrue(supersForLocation.contains(mySuper));
        assertTrue(supersForLocation.contains(mySuper2));

        //making sure that the the 3rd super wasn't added to that location
        assertFalse(supersForLocation.contains(mySuper3));

    }

    /**
     * Test of getAllSuperherosForOrganization method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetAllSuperherosForOrganization() {
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Superhero mySuper = new Superhero();
        mySuper.setSuperheroName("Not Your Ordinary Guy");
        mySuper.setSuperheroDescription("actually listens and tries to understand");
        mySuper.setPower(power);
        superheroDao.addSuperhero(mySuper);

        Power power2 = new Power();
        power2.setPowerName("listening to people");
        power2 = powerDao.addPower(power2);

        Superhero mySuper2 = new Superhero();
        mySuper2.setSuperheroName("Another great superhero");
        mySuper2.setSuperheroDescription("doesn't judge");
        mySuper2.setPower(power2);
        superheroDao.addSuperhero(mySuper2);

        Power power3 = new Power();
        power3.setPowerName("being invisible");
        power3 = powerDao.addPower(power3);

        Superhero mySuper3 = new Superhero();
        mySuper3.setSuperheroName("Third superhero guy");
        mySuper3.setSuperheroDescription("sometimes disappears");
        mySuper3.setPower(power3);
        superheroDao.addSuperhero(mySuper3);

        //checking if all 3 supers were added to DB
        List<Superhero> supers = superheroDao.getAllSuperheros();
        assertEquals(3, supers.size());
        assertTrue(supers.contains(mySuper));
        assertTrue(supers.contains(mySuper2));
        assertTrue(supers.contains(mySuper3));

        //adding organization and 2 out of 3 supers to it
        Location superLocation = new Location();
        superLocation.setLocationName("Super Location");
        superLocation.setDescription("You can't beat it");
        superLocation.setAddress("101 Bedford Ave");
        superLocation.setCity("Brooklyn");
        superLocation.setState("NY");
        superLocation.setZip("11211");
        superLocation.setLatitude("40.720239");
        superLocation.setLongitude("-73.954620");
        locationDao.addLocation(superLocation);

        Organization superOrg = new Organization();
        superOrg.setOrgName("True superheroes");
        superOrg.setDescription("True superheroes are among us");
        superOrg.setPhoneNumber("3475937574");
        superOrg.setLocation(superLocation);
        ArrayList<Superhero> mySupers = new ArrayList<>();
        mySupers.add(mySuper);
        mySupers.add(mySuper2);
        superOrg.setListOfSuperheroes(mySupers);
        organizationDao.addOrganization(superOrg);

        List<Superhero> supersForOrganization = superheroDao.getAllSuperherosForOrganization(superOrg);
        assertEquals(2, supersForOrganization.size());
        assertTrue(supersForOrganization.contains(mySuper));
        assertTrue(supersForOrganization.contains(mySuper2));
        assertFalse(supersForOrganization.contains(mySuper3));
    }

    /**
     * Test of getAllSuperherosForOrganization method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetAllSuperheroesWithThatPower () {
        //create 2 powers
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerName("listening to people");
        power2 = powerDao.addPower(power2);

        //create 3 superheroes
        //assign the first power to 2 supers
        //assign the second power to the third one
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

//        //assign the first power to 2 supers
//        mySuper.setPower(power);
//        mySuper2.setPower(power);
//
//
//        mySuper3.setPower(power2);

        //act
        List<Superhero> supersWithThatPower = superheroDao.getAllSuperheroesWithThatPower(power.getPowerId());

        //check if the method gets 2 supers with the same power and not the third one
        assertEquals(2, supersWithThatPower.size());
        assertTrue(supersWithThatPower.contains(mySuper));
        assertTrue(supersWithThatPower.contains(mySuper2));
        assertFalse(supersWithThatPower.contains(mySuper3));

    }
}
