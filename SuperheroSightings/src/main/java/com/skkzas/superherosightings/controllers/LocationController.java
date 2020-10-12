package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.*;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    Set<ConstraintViolation<Location>> violationsEdit = new HashSet<>();

    @GetMapping("locations")
    public String displayAllLocations(Model model) {
        List<Location> allLocations = locationDao.getAllLocations();

        model.addAttribute("allLocations", allLocations);
        model.addAttribute("errors", violations);

        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {

        String locationName = request.getParameter("locationName");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String description = request.getParameter("description");
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");

        Location location = new Location();
        location.setLocationName(locationName);
        location.setAddress(address);
        location.setCity(city);
        location.setState(state);
        location.setZip(zip);
        location.setDescription(description);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if (violations.isEmpty()) {
            //FIXME: User can try to get the map even though all fields are not filled out!
            locationDao.addLocation(location);
        }

        return "redirect:/locations";
    }

    @GetMapping("locationDetails")
    public String locationDetails(Integer id, Model model) {
        Location theLocation = locationDao.getLocationById(id);
        BigDecimal latitude = new BigDecimal(theLocation.getLatitude());
        BigDecimal longitude = new BigDecimal(theLocation.getLongitude());
        String locationName = theLocation.getLocationName();

        List<Superhero> allSuperheroesForLocation = superheroDao.getAllSuperheroesForLocation(theLocation);

        model.addAttribute("location", theLocation);
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        model.addAttribute("locationName", locationName);
        model.addAttribute("superheroes", allSuperheroesForLocation);

        return "locationDetails";
    }

    @GetMapping("locationEdit")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        model.addAttribute("errors", violationsEdit);

        return "locationEdit";
    }

    @PostMapping("locationEdit")
    public String performLocationEdit(Model model, HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/locations";
        }

        int locationId = Integer.parseInt(request.getParameter("locationId"));

        Location location = locationDao.getLocationById(locationId);

        String locationName = request.getParameter("locationName");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String description = request.getParameter("description");
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

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsEdit = validate.validate(location);

        if (violationsEdit.isEmpty()) {
            locationDao.updateLocation(location);
            return "redirect:/locationDetails?id=" + location.getLocationId();
        } else {
            model.addAttribute("location", location);
            model.addAttribute("errors", violationsEdit);

            return "locationEdit";
        }
    }

    @GetMapping("locationDelete")
    public String deleteLocation(HttpServletRequest request, Model model) {
        //use the delete Dao function to determine which other tables are affected
        //for each table, get all items based on this location id.
        //send each list to the page
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        List<Organization> organizations = organizationDao.getOrganizationsForLocation(location);
        List<Sighting> sightings = sightingDao.getAllSightingsForLocation(location);
        List<Superhero> superheroes = new ArrayList<>();

        model.addAttribute("location", location);
        model.addAttribute("organizations", organizations);
        model.addAttribute("sightings", sightings);

        return "locationDelete";
    }

    @GetMapping("locationDeleteConfirm")
    public String performDeleteSuperhero(HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/locations";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);

        locationDao.deleteLocationById(location.getLocationId());

        return "redirect:/locations";
    }

}
