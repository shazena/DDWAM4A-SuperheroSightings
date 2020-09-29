/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author kristinazakharova
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerDaoDBTest {
    
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
    
    public PowerDaoDBTest() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Power> powers = powerDao.getAllPowers();
        for (Power power : powers) {
            powerDao.deletePowerById(power.getPowerId());
        }
    }

    /**
     * Test of getPowerById and AddPower methods, of class PowerDaoDB.
     */
    @Test
    public void testAddAndGetPower() {
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
    }

    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Power power2 = new Power();
        power.setPowerName("listening to people");
        power2 = powerDao.addPower(power);

        List<Power> powers = powerDao.getAllPowers();

        assertEquals(2, powers.size());
        assertTrue(powers.contains(power));
        assertTrue(powers.contains(power2));
    }

    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setPowerName("flying");
        power = powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);

        power.setPowerName("listening to people");
        powerDao.updatePower(power);

        assertNotEquals(power, fromDao);

        fromDao = powerDao.getPowerById(power.getPowerId());

        assertEquals(power, fromDao);
    }

    /**
     * Test of deletePowerById method, of class PowerDaoDB.
     */
    @Test
    public void testDeletePowerById() {
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

        Sighting firstSighting = new Sighting();
        LocalDate date = LocalDate.parse("2020-01-08"); //not sure about the format yet
        firstSighting.setDate(date);
        firstSighting.setLocation(superLocation);
        firstSighting.setSuperhero(mySuper);
        sightingDao.addSighting(firstSighting);

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);

        powerDao.deletePowerById(power.getPowerId());

        fromDao = powerDao.getPowerById(power.getPowerId());
        assertNull(fromDao);
    }
}
