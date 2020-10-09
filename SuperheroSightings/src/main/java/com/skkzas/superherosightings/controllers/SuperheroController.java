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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Oct 4, 2020
 */
@Controller
public class SuperheroController {

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

    @GetMapping("superheroes")
    public String displayAllSuperheroes(Model model) {
        List<Superhero> allSuperheroes = superheroDao.getAllSuperheros();
        List<Power> powers = powerDao.getAllPowers();

        model.addAttribute("allSuperheroes", allSuperheroes);
        model.addAttribute("powers", powers);

        return "superheroes";
    }

    @PostMapping("addSuperhero")
    public String addSuperhero(Superhero superhero, HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("superheroDescription");

        Power power = new Power();
        String powerId = request.getParameter("powerExisting");
        if (powerId != null) {
            power = powerDao.getPowerById(Integer.parseInt(powerId));
        } else {
            String powerName = request.getParameter("powerName");
            power.setPowerName(powerName);
            power = powerDao.addPower(power);
        }

        superhero.setSuperheroName(name);
        superhero.setPower(power);
        superhero.setSuperheroDescription(description);

        superheroDao.addSuperhero(superhero);

        return "redirect:/superheroes";
    }

    @GetMapping("superheroDetails")
    public String superheroDetails(Integer id, Model model) {
        Superhero theSuperhero = superheroDao.getSuperheroById(id);
        model.addAttribute("superhero", theSuperhero);
        return "superheroDetails";
    }

    @GetMapping("superheroDelete")
    public String deleteSuperhero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        List<Sighting> sightings = sightingDao.getAllSightingsForListOfSuperheros(superheroes);
        List<Location> locations = new ArrayList<>();

        for (Sighting sighting : sightings) {
            Location locationForSighting = sighting.getLocation();
            locations.add(locationForSighting);
        }

        model.addAttribute("superhero", superhero);
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        return "superheroDelete";
    }

    @GetMapping("superheroDeleteConfirm")
    public String performDeleteSuperhero(HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/superheroes";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);

        superheroDao.deleteSuperheroById(superhero.getSuperheroId());

        return "redirect:/superheroes";
    }

    @GetMapping("superheroEdit")
    public String editSuperhero(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("superhero", superhero);
        model.addAttribute("powers", powers);

        return "superheroEdit";
    }

    @PostMapping("superheroEdit")
    public String performSuperheroEdit(HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/superheroes";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);

        String name = request.getParameter("name");
        String powerId = request.getParameter("powerId");
        String description = request.getParameter("description");

        superhero.setSuperheroName(name);
        superhero.setPower(powerDao.getPowerById(Integer.parseInt(powerId)));
        superhero.setSuperheroDescription(description);

        superheroDao.updateSuperhero(superhero);

        return "redirect:/superheroes";
    }
}
