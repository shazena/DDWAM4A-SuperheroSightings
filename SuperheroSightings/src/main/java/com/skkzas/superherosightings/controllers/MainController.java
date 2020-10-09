package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.LocationDao;
import com.skkzas.superherosightings.dao.OrganizationDao;
import com.skkzas.superherosightings.dao.PowerDao;
import com.skkzas.superherosightings.dao.SightingDao;
import com.skkzas.superherosightings.dao.SuperheroDao;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Sighting;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Oct 9, 2020
 */
@Controller
public class MainController {

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

    @GetMapping("/")
    public String displayAllLocations(Model model) {

        List<Sighting> lastTenSightings = sightingDao.getLastTenSightings();

        List<String> formattedSightings = new ArrayList<String>();

        for (Sighting sighting : lastTenSightings) {
            String sightingFormatted = "On " + sighting.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    + " " + sighting.getSuperhero().getSuperheroName() + " was sighted at " + sighting.getLocation().getLocationName();
            formattedSightings.add(sightingFormatted);
        }

        model.addAttribute("formattedSightings", formattedSightings);
        model.addAttribute("lastTenSightings", lastTenSightings);
        return "index";
    }
}
