package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.LocationDao;
import com.skkzas.superherosightings.dao.OrganizationDao;
import com.skkzas.superherosightings.dao.PowerDao;
import com.skkzas.superherosightings.dao.SightingDao;
import com.skkzas.superherosightings.dao.SuperheroDao;
import com.skkzas.superherosightings.dto.Location;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Oct 4, 2020
 */
@Controller
public class LocationController {

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

    @GetMapping("locations")
    public String displayAllLocations(Model model) {
        List<Location> allLocations = locationDao.getAllLocations();

        model.addAttribute("allLocations", allLocations);

        return "locations";
    }

    @GetMapping("locationDetails")
    public String locationDetails(Integer id, Model model) {
        Location theLocation = locationDao.getLocationById(id);
        model.addAttribute("location", theLocation);
        return "locationDetails";
    }

    @GetMapping("locationEdit")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);

        return "locationEdit";
    }

    @GetMapping("locationDelete")
    public String deleteLocation(Integer id, Model model) {
        //use the delete Dao function to determine which other tables are affected
        //for each table, get all items based on this location id.
        //send each list to the page

        return "locationDelete";
    }

}
