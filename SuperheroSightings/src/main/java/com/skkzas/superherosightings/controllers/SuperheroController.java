package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        model.addAttribute("allSuperheroes", allSuperheroes);

        return "superheroes";
    }

    @GetMapping("superheroDetails")
    public String superheroDetails(Integer id, Model model) {
        Superhero theSuperhero = superheroDao.getSuperheroById(id);
        model.addAttribute("superhero", theSuperhero);
        return "superheroDetails";
    }
}
