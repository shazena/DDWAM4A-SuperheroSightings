package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Organization;
import com.skkzas.superherosightings.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Oct 4, 2020
 */
@Controller
public class OrganizationController {

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

    @GetMapping("organizations")
    public String displayAllOrganizations(Model model) {
        List<Organization> allOrganizations = organizationDao.getAllOrganizations();
        List<Location> locations = locationDao.getAllLocations();
        List<Superhero> listOfSuperheroes = superheroDao.getAllSuperheros();

        for (Organization organization : allOrganizations) {

            String unformattedPhoneNumber = organization.getPhoneNumber();
            String formattedPhoneNumber = "("
                    + unformattedPhoneNumber.substring(0, 3)
                    + ") "
                    + unformattedPhoneNumber.substring(3, 6)
                    + "-"
                    + unformattedPhoneNumber.substring(6, 10);
            organization.setPhoneNumber(formattedPhoneNumber);
        }

        model.addAttribute("allOrganizations", allOrganizations);
        model.addAttribute("locations", locations);
        model.addAttribute("listOfSuperheroes", listOfSuperheroes);
        model.addAttribute("organization", new Organization());

        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) {
        String name = request.getParameter("orgName");

        String phoneFormatted = request.getParameter("phoneNumber");
        String phoneUnformatted = phoneFormatted.replaceAll("[^\\d.]", "");

        String description = request.getParameter("description");

        String[] superheroIds = request.getParameterValues("superheroId");

        List<Superhero> listOfSuperheroes = new ArrayList<>();
        if (superheroIds != null) {
            for (String superheroId : superheroIds) {
                listOfSuperheroes.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
            }
        } else {
            FieldError error = new FieldError("organization", "listOfSuperheroes", "Must include one superhero");
            result.addError(error);
        }

        //get the location
        Location location = new Location();

        String locationId = request.getParameter("locationId");
        location = locationDao.getLocationById(Integer.parseInt(locationId));

        organization.setOrgName(name);
        organization.setPhoneNumber(phoneUnformatted);
        organization.setLocation(location);
        organization.setDescription(description);
        organization.setListOfSuperheroes(listOfSuperheroes);

        if (result.hasErrors()) {
            List<Organization> allOrganizations = organizationDao.getAllOrganizations();

            for (Organization organizationAdd : allOrganizations) {

                String unformattedPhoneNumber = organizationAdd.getPhoneNumber();
                String formattedPhoneNumber = "("
                        + unformattedPhoneNumber.substring(0, 3)
                        + ") "
                        + unformattedPhoneNumber.substring(3, 6)
                        + "-"
                        + unformattedPhoneNumber.substring(6, 10);
                organizationAdd.setPhoneNumber(formattedPhoneNumber);
            }

            model.addAttribute("allOrganizations", allOrganizations);
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("listOfSuperheroes", superheroDao.getAllSuperheros());
            model.addAttribute("organization", organization);
            return "organizations";
        }

        organizationDao.addOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("organizationDetails")
    public String organizationDetails(Integer id, Model model) {
        Organization theOrganization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", theOrganization);
        return "organizationDetails";
    }

    @GetMapping("organizationDelete")
    public String deleteOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);

        model.addAttribute("organization", organization);
        return "organizationDelete";
    }

    @GetMapping("organizationDeleteConfirm")
    public String performDeletePower(HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/organizations";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);

        organizationDao.deleteOrganizationById(organization.getOrgId());

        return "redirect:/organizations";
    }

    @GetMapping("organizationEdit")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Location> locations = locationDao.getAllLocations();
        List<Superhero> superheroes = superheroDao.getAllSuperheros();

        String unformattedPhoneNumber = organization.getPhoneNumber();
//        String formattedPhoneNumber = "("
//                + unformattedPhoneNumber.substring(0, 3)
//                + ") "
//                + unformattedPhoneNumber.substring(3, 6)
//                + "-"
//                + unformattedPhoneNumber.substring(6, 10);
//        organization.setPhoneNumber(formattedPhoneNumber);

        model.addAttribute("organization", organization);
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("formattedPhoneNumber", unformattedPhoneNumber);

        return "organizationEdit";
    }

    @PostMapping("organizationEdit")
    public String performSuperheroEdit(@RequestBody @Valid Organization organization, BindingResult result, HttpServletRequest request, @RequestParam(value = "action", required = true) String action, Model model) {
        if (action.equals("cancel")) {
            return "redirect:/organizations";
        }

        String name = request.getParameter("orgName");
        String phoneFormatted = request.getParameter("phoneNumber");
        String phoneUnformatted = phoneFormatted.replaceAll("[^\\d.]", "");

        String locationId = request.getParameter("locationId");
        String description = request.getParameter("description");
        String[] superheroIds = request.getParameterValues("listOfSuperheroes");

        List<Superhero> superheroes = new ArrayList<>();
        if (superheroIds != null) {
            for (String superheroId : superheroIds) {
                superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
            }
        } else {
            FieldError error = new FieldError("organization", "listOfSuperheroes", "Must include one superhero");
            result.addError(error);
        }

        organization.setOrgName(name);
        organization.setPhoneNumber(phoneUnformatted);
        organization.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        organization.setDescription(description);
        organization.setListOfSuperheroes(superheroes);

        if (result.hasErrors()) {
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("superheroes", superheroDao.getAllSuperheros());
            model.addAttribute("organization", organization);
            return "organizationEdit";
        }

        organizationDao.updateOrganization(organization);

        return "redirect:/organizationDetails?id=" + organization.getOrgId();
    }
}
