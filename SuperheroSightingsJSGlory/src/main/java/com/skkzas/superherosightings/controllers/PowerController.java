package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Power;
import com.skkzas.superherosightings.dto.Sighting;
import com.skkzas.superherosightings.dto.Superhero;
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
public class PowerController {

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

    Set<ConstraintViolation<Power>> violations = new HashSet<>();
    Set<ConstraintViolation<Power>> violationsEdit = new HashSet<>();

    @GetMapping("powers")
    public String displayAllPowers(Model model) {
        List<Power> allPowers = powerDao.getAllPowers();

        model.addAttribute("allPowers", allPowers);
        violations.clear();
        model.addAttribute("errors", violations);

        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(Model model, HttpServletRequest request) {
        String name = request.getParameter("name");

        Power power = new Power();
        power.setPowerName(name);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(power);

        if (violations.isEmpty()) {
        }

        if (violations.isEmpty()) {
            powerDao.addPower(power);
            return "redirect:/powers";
        } else {
            List<Power> allPowers = powerDao.getAllPowers();
            model.addAttribute("allPowers", allPowers);
            model.addAttribute("errors", violations);
            return "powers";
        }

    }

    @GetMapping("powerDelete")
    public String deletePower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);
        List<Superhero> superheroes = superheroDao.getAllSuperheroesWithThatPower(id);
        List<Sighting> sightings = sightingDao.getAllSightingsForListOfSuperheros(superheroes);

        model.addAttribute("power", power);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("sightings", sightings);

        return "powerDelete";
    }

    @GetMapping("powerDeleteConfirm")
    public String performDeletePower(HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/powers";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);

        powerDao.deletePowerById(power.getPowerId());

        return "redirect:/powers";
    }

    @GetMapping("powerEdit")
    public String editPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);

        model.addAttribute("power", power);
        violationsEdit.clear();
        model.addAttribute("errors", violationsEdit);
        return "powerEdit";
    }

    @PostMapping("powerEdit")
    public String performEditPower(Model model, HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/powers";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);

        power.setPowerName(request.getParameter("name"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsEdit = validate.validate(power);

        if (violationsEdit.isEmpty()) {
            powerDao.updatePower(power);
            return "redirect:/powers";
        } else {
            model.addAttribute("power", power);
            model.addAttribute("errors", violationsEdit);
            return "powerEdit";
        }

    }
}
