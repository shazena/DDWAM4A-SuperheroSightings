package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.LocationDao;
import com.skkzas.superherosightings.dao.OrganizationDao;
import com.skkzas.superherosightings.dao.PowerDao;
import com.skkzas.superherosightings.dao.SightingDao;
import com.skkzas.superherosightings.dao.SuperheroDao;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Power;
import com.skkzas.superherosightings.dto.Sighting;
import com.skkzas.superherosightings.dto.Superhero;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Oct 4, 2020
 */
@Controller
public class SightingController {

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

    @GetMapping("sightings")
    public String displayAllSightings(Model model) {

        List<Sighting> allSightings = sightingDao.getAllSightings();
        List<Superhero> allSuperheroes = superheroDao.getAllSuperheros();
        List<Location> allLocations = locationDao.getAllLocations();
        List<Power> allPowers = powerDao.getAllPowers();

        model.addAttribute("allSightings", allSightings);
        model.addAttribute("allSuperheroes", allSuperheroes);
        model.addAttribute("allLocations", allLocations);
        model.addAttribute("allPowers", allPowers);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addLocation(HttpServletRequest request) {

        //get the date and parse it
        String dateFromPage = request.getParameter("date");
        LocalDate dateOfSighting = LocalDate.parse(dateFromPage, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        //get the superhero And Power
        Power power = new Power();
        Superhero superhero = new Superhero();

        String superheroId = request.getParameter("superheroExisting");
        if (superheroId != null) {
            superhero = superheroDao.getSuperheroById(Integer.parseInt(superheroId));
        } else {
            String superheroName = request.getParameter("superheroName");
            String superheroDescription = request.getParameter("superheroDescription");

            String powerId = request.getParameter("powerExisting");
            if (powerId != null) {
                power = powerDao.getPowerById(Integer.parseInt(powerId));
            } else {
                String powerName = request.getParameter("powerName");
                power.setPowerName(powerName);
                power = powerDao.addPower(power);
            }

            superhero.setSuperheroName(superheroName);
            superhero.setPower(power);
            superhero.setSuperheroDescription(superheroDescription);

            superhero = superheroDao.addSuperhero(superhero);
        }

        //get the location
        Location location = new Location();

        String locationId = request.getParameter("locationExisting");
        if (locationId != null) {
            location = locationDao.getLocationById(Integer.parseInt(locationId));
        } else {

            String locationName = request.getParameter("locationName");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            String description = request.getParameter("locationDescription");
            String longitude = request.getParameter("longitude");
            String latitude = request.getParameter("latitude");

            location.setLocationName(locationName);
            location.setAddress(address);
            location.setCity(city);
            location.setState(state);
            location.setZip(zip);
            location.setDescription(description);
            location.setLongitude(longitude);
            location.setLatitude(latitude);

            locationDao.addLocation(location);
        }

        Sighting sighting = new Sighting();
        sighting.setDate(dateOfSighting);
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);
        sightingDao.addSighting(sighting);
        return "redirect:/sightings";
    }

    @GetMapping("sightingDetails")
    public String sightingDetails(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);

        model.addAttribute(sighting);

        //TODO get the map to show up on this page too!!!
        return "sightingDetails";
    }

}
