/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 29, 2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {
    
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
    
    public OrganizationDaoDBTest() {
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
     * Test of getOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganization() {
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
        superLocation.setLongitude("-73.9546197");
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

        Organization fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());
        assertEquals(superOrg, fromDao);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        //Org1
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
        superLocation.setLongitude("-73.9546197");
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

        //Org2
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
        locationDao.addLocation(superLocation2);

        Organization superOrg2 = new Organization();
        superOrg2.setOrgName("True superheroes 2");
        superOrg2.setDescription("True superheroes are among us 2");
        superOrg2.setPhoneNumber("3475937511");
        superOrg2.setLocation(superLocation2);
        ArrayList<Superhero> supers2 = new ArrayList<>();
        supers.add(mySuper2);
        superOrg.setListOfSuperheroes(supers2);
        organizationDao.addOrganization(superOrg2);

        //checking if the DB contains both
        List<Organization> orgs = organizationDao.getAllOrganizations();

        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(superOrg));
        assertTrue(orgs.contains(superOrg2));
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
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
        superLocation.setLongitude("-73.9546197");
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

        Organization fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());
        assertEquals(superOrg, fromDao);

        //updating name
        superOrg.setOrgName("new super name");
        organizationDao.updateOrganization(superOrg);

        assertNotEquals(superOrg, fromDao);

        fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());

        assertEquals(superOrg, fromDao);

        //updating description
        superOrg.setDescription("new super description");
        organizationDao.updateOrganization(superOrg);

        assertNotEquals(superOrg, fromDao);

        fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());

        assertEquals(superOrg, fromDao);

        //updating phone number
        superOrg.setPhoneNumber("3475876543");
        organizationDao.updateOrganization(superOrg);

        assertNotEquals(superOrg, fromDao);

        fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());

        assertEquals(superOrg, fromDao);

        //updating location (creating new location too)
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

        superOrg.setLocation(superLocation2);
        organizationDao.updateOrganization(superOrg);

        assertNotEquals(superOrg, fromDao);

        fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());

        assertEquals(superOrg, fromDao);

        //updating list of supers (adding a new one)
        Power power2 = new Power();
        power2.setPowerName("listening to people");
        power2 = powerDao.addPower(power2);

        Superhero mySuper2 = new Superhero();
        mySuper2.setSuperheroName("Another great superhero");
        mySuper2.setSuperheroDescription("doesn't judge");
        mySuper2.setPower(power2);
        superheroDao.addSuperhero(mySuper2);

        supers.add(mySuper2);
        superOrg.setListOfSuperheroes(supers);
        organizationDao.updateOrganization(superOrg);

        assertNotEquals(superOrg, fromDao);

        fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());

        assertEquals(superOrg, fromDao);
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() {
        //arrange
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
        superLocation.setLongitude("-73.9546197");
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

        Organization fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());
        assertEquals(superOrg, fromDao);

        //act
        organizationDao.deleteOrganizationById(superOrg.getOrgId());

        //assert
        fromDao = organizationDao.getOrganizationById(superOrg.getOrgId());
        assertNull(fromDao);
    }
    
}
