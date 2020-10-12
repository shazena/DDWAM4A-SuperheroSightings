package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

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

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    Set<ConstraintViolation<Sighting>> violationsEdit = new HashSet<>();

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
        violations.clear();
        model.addAttribute("errors", violations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(Model model, HttpServletRequest request) {

        //get the date and parse it
        String dateFromPage = request.getParameter("date");
        LocalDate dateOfSighting;
        if (dateFromPage == null || dateFromPage.isBlank()) {
            dateOfSighting = null;
        } else {
            dateOfSighting = LocalDate.parse(dateFromPage, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

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
//                power = powerDao.addPower(power);
            }
            superhero.setSuperheroName(superheroName);
            superhero.setPower(power);
            superhero.setSuperheroDescription(superheroDescription);
        }//end of else

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

        }

        Sighting sighting = new Sighting();
        sighting.setDate(dateOfSighting);
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            if (sighting.getSuperhero().getPower().getPowerId() == 0) {
                power = powerDao.addPower(power);
            }
            if (sighting.getSuperhero().getSuperheroId() == 0) {
                superhero = superheroDao.addSuperhero(superhero);
            }
            if (location.getLocationId() == 0) {
                location = locationDao.addLocation(location);
            }
            sightingDao.addSighting(sighting);

            return "redirect:/sightings";

        } else {
            List<Sighting> allSightings = sightingDao.getAllSightings();
            List<Superhero> allSuperheroes = superheroDao.getAllSuperheros();
            List<Location> allLocations = locationDao.getAllLocations();
            List<Power> allPowers = powerDao.getAllPowers();

            model.addAttribute("allSightings", allSightings);
            model.addAttribute("allSuperheroes", allSuperheroes);
            model.addAttribute("allLocations", allLocations);
            model.addAttribute("allPowers", allPowers);
            model.addAttribute("errors", violations);
            return "sightings";
        }

    }

    @GetMapping("sightingDetails")
    public String sightingDetails(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);

        model.addAttribute(sighting);

        return "sightingDetails";
    }

    @GetMapping("sightingDelete")
    public String deleteSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));

        Sighting sighting = sightingDao.getSightingById(id);
        Superhero superhero = superheroDao.getSuperheroForSighting(sighting.getSightingId());

        model.addAttribute("sighting", sighting);
        model.addAttribute("superhero", superhero);

        return "sightingDelete";
    }

    @GetMapping("sightingDeleteConfirm")
    public String performDeleteSuperhero(HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/sightings";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);

        sightingDao.deleteSightingById(sighting.getSightingId());

        return "redirect:/sightings";
    }

    @GetMapping("sightingEdit")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<Superhero> allSuperheroes = superheroDao.getAllSuperheros();
        List<Location> allLocations = locationDao.getAllLocations();

        model.addAttribute("sighting", sighting);
        model.addAttribute("allSuperheroes", allSuperheroes);
        model.addAttribute("allLocations", allLocations);
        violationsEdit.clear();
        model.addAttribute("errors", violationsEdit);

        return "sightingEdit";
    }

    @PostMapping("sightingEdit")
    public String performSightingEdit(Model model, HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/sightings";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);

        String date = request.getParameter("date");
        LocalDate dateOfSighting;
        if (date == null || date.isBlank()) {
            dateOfSighting = null;
        } else {
            dateOfSighting = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        String superheroId = request.getParameter("superheroId");
        String locationId = request.getParameter("locationId");

        sighting.setDate(dateOfSighting);
        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsEdit = validate.validate(sighting);

        if (violationsEdit.isEmpty()) {
            sightingDao.updateSighting(sighting);
            return "redirect:/sightingDetails?id=" + sighting.getSightingId();
        } else {
            model.addAttribute("sighting", sighting);
            model.addAttribute("allSuperheroes", superheroDao.getAllSuperheros());
            model.addAttribute("allLocations", locationDao.getAllLocations());
            model.addAttribute("errors", violationsEdit);
            return "sightingEdit";
        }
    }

}
