package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Organization;
import com.skkzas.superherosightings.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        List<Superhero> superheroes = superheroDao.getAllSuperheros();

        for (Organization organization : allOrganizations) {

            String unformattedPhoneNumber = organization.getPhoneNumber();
            String formattedPhoneNumber = "("
                    + unformattedPhoneNumber.substring(0, 3)
                    + ") "
                    + unformattedPhoneNumber.substring(3, 6)
                    + " - "
                    + unformattedPhoneNumber.substring(6, 10);
            organization.setPhoneNumber(formattedPhoneNumber);
        }

        model.addAttribute("allOrganizations", allOrganizations);
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);

        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String name = request.getParameter("name");

        String phoneFormatted = request.getParameter("phoneNum");
        String phoneUnformatted = phoneFormatted.replaceAll("[^\\d.]", "");

        String locationId = request.getParameter("locationId");
        String description = request.getParameter("description");
        String[] superheroIds = request.getParameterValues("superheroId");

        organization.setOrgName(name);
        organization.setPhoneNumber(phoneUnformatted);
        organization.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        organization.setDescription(description);

        List<Superhero> superheroes = new ArrayList<>();
        for (String superheroId : superheroIds) {
            superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        }
        organization.setListOfSuperheroes(superheroes);

        organizationDao.addOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("organizationDetails")
    public String organizationDetails(Integer id, Model model) {
        Organization theOrganization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", theOrganization);
        return "organizationDetails";
    }

    @GetMapping("organizationEdit")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Location> locations = locationDao.getAllLocations();
        List<Superhero> superheroes = superheroDao.getAllSuperheros();

        String unformattedPhoneNumber = organization.getPhoneNumber();
        String formattedPhoneNumber = "("
                + unformattedPhoneNumber.substring(0, 3)
                + ") "
                + unformattedPhoneNumber.substring(3, 6)
                + " - "
                + unformattedPhoneNumber.substring(6, 10);
        organization.setPhoneNumber(formattedPhoneNumber);

        model.addAttribute("organization", organization);
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("formattedPhoneNumber", formattedPhoneNumber);

        return "organizationEdit";
    }

    @PostMapping("organizationEdit")
    public String performSuperheroEdit(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);
        String name = request.getParameter("name");
        String phoneFormatted = request.getParameter("phoneNum");
        String phoneUnformatted = phoneFormatted.replaceAll("[^\\d.]", "");

        String locationId = request.getParameter("locationId");
        String description = request.getParameter("description");
        String[] superheroIds = request.getParameterValues("superheroId");

        organization.setOrgName(name);
        organization.setPhoneNumber(phoneUnformatted);
        organization.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        organization.setDescription(description);

        List<Superhero> superheroes = new ArrayList<>();
        for (String superheroId : superheroIds) {
            superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        }
        organization.setListOfSuperheroes(superheroes);

        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }
}
