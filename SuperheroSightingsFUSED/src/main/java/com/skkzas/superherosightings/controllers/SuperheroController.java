package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Power;
import com.skkzas.superherosightings.dto.Sighting;
import com.skkzas.superherosightings.dto.Superhero;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    ImageDao imageDao;

    Set<ConstraintViolation<Superhero>> violations = new HashSet<>();
    Set<ConstraintViolation<Superhero>> violationsEdit = new HashSet<>();

    private final String SUPERHERO_UPLOAD_DIRECTORY = "Superheroes";

    @GetMapping("superheroes")
    public String displayAllSuperheroes(Model model) {
        List<Superhero> allSuperheroes = superheroDao.getAllSuperheros();
        List<Power> powers = powerDao.getAllPowers();

        model.addAttribute("allSuperheroes", allSuperheroes);
        model.addAttribute("powers", powers);
        violations.clear();
        model.addAttribute("errors", violations);

        return "superheroes";
    }

    @PostMapping("addSuperhero")
    public String addSuperhero(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String fileLocation = imageDao.saveImage(file, Long.toString(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), SUPERHERO_UPLOAD_DIRECTORY);

        String name = request.getParameter("name");
        String description = request.getParameter("superheroDescription");

        Superhero superhero = new Superhero();

        Power power = new Power();
        String powerId = request.getParameter("powerExisting");
        if (powerId != null) {
            power = powerDao.getPowerById(Integer.parseInt(powerId));
        } else {
            String powerName = request.getParameter("powerName");
            power.setPowerName(powerName);
        }

        superhero.setSuperheroName(name);
        superhero.setPower(power);
        superhero.setSuperheroDescription(description);
        superhero.setPhotoFileName(fileLocation);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superhero);

        if (violations.isEmpty()) {
            if (superhero.getPower().getPowerId() == 0) {
                power = powerDao.addPower(power);
            }
            superheroDao.addSuperhero(superhero);
            return "redirect:/superheroes";
        } else {
            List<Superhero> allSuperheroes = superheroDao.getAllSuperheros();
            List<Power> powers = powerDao.getAllPowers();
            model.addAttribute("allSuperheroes", allSuperheroes);
            model.addAttribute("powers", powers);
            model.addAttribute("errors", violations);
            return "superheroes";
        }

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

        model.addAttribute("superhero", superhero);
        model.addAttribute("sightings", sightings);
        return "superheroDelete";
    }

    @GetMapping("superheroDeleteConfirm")
    public String performDeleteSuperhero(HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/superheroes";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);
        imageDao.deleteImage(superhero.getPhotoFileName());

        superheroDao.deleteSuperheroById(superhero.getSuperheroId());

        return "redirect:/superheroes";
    }

    @GetMapping("superheroEdit")
    public String editSuperhero(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("superhero", superhero);
        model.addAttribute("powers", powers);
        violationsEdit.clear();
        model.addAttribute("errors", violationsEdit);

        return "superheroEdit";
    }

    @PostMapping("superheroEdit")
    public String performSuperheroEdit(Model model, HttpServletRequest request, @RequestParam(value = "action", required = true) String action, @RequestParam("file") MultipartFile file) {
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

        boolean fileIsEmpty = file.isEmpty();

        if (!fileIsEmpty) {
            superhero.setPhotoFileName(imageDao.updateImage(file, superhero.getPhotoFileName(), SUPERHERO_UPLOAD_DIRECTORY));
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsEdit = validate.validate(superhero);

        if (violationsEdit.isEmpty()) {
            superheroDao.updateSuperhero(superhero);
            return "redirect:/superheroDetails?id=" + superhero.getSuperheroId();
        } else {
            model.addAttribute("superhero", superhero);
            List<Power> powers = powerDao.getAllPowers();
            model.addAttribute("powers", powers);
            model.addAttribute("errors", violationsEdit);
            return "superheroEdit";
        }

    }
}
