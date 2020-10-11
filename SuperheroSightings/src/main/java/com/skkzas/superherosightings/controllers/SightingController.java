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
        model.addAttribute("errors", violations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {

        //get the date and parse it
        String dateFromPage = request.getParameter("date");

        LocalDate dateOfSighting;
        if (dateFromPage == null || dateFromPage.isBlank()) {
            dateOfSighting = null;
        } else {
            dateOfSighting = LocalDate.parse(dateFromPage, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        //get the superhero And Power
        Superhero superhero = new Superhero();

        String superheroId = request.getParameter("superheroExisting");
        superhero = superheroDao.getSuperheroById(Integer.parseInt(superheroId));

        String locationId = request.getParameter("locationExisting");
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));

        Sighting sighting = new Sighting();
        sighting.setDate(dateOfSighting);
        sighting.setLocation(location);
        sighting.setSuperhero(superhero);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        } else {
//            model.addAttribute(superhero);
//            List<Power> powers = powerDao.getAllPowers();
//            model.addAttribute("powers", powers);
//            model.addAttribute("errors", violationsEdit);
//            return "superheroEdit";
        }
        return "redirect:/sightings";

    }

    @GetMapping("sightingDetails")
    public String sightingDetails(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);

        model.addAttribute(sighting);

        //TODO get the map to show up on this page too!!!
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
