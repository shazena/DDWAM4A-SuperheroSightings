package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @GetMapping("powers")
    public String displayAllPowers(Model model) {
        List<Power> allPowers = powerDao.getAllPowers();

        model.addAttribute("allPowers", allPowers);

        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {
        String name = request.getParameter("name");

        Power power = new Power();
        power.setPowerName(name);

        powerDao.addPower(power);

        return "redirect:/powers";
    }

//    @GetMapping("deletePower")
//    public String deletePower(HttpServletRequest request) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        powerDao.deletePowerById(id);
//
//        return "redirect:/powers";
//    }

    @GetMapping("powerEdit")
    public String editPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);

        model.addAttribute("power", power);
        return "powerEdit";
    }

    @PostMapping("powerEdit")
    public String performEditPower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);

        power.setPowerName(request.getParameter("name"));

        powerDao.updatePower(power);

        return "redirect:/powers";
    }
}
