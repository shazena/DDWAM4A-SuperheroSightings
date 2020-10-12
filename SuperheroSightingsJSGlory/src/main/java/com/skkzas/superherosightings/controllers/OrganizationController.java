package com.skkzas.superherosightings.controllers;

import com.skkzas.superherosightings.dao.*;
import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Organization;
import com.skkzas.superherosightings.dto.Power;
import com.skkzas.superherosightings.dto.Superhero;
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

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    Set<ConstraintViolation<Organization>> violationsEdit = new HashSet<>();

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
                    + "-"
                    + unformattedPhoneNumber.substring(6, 10);
            organization.setPhoneNumber(formattedPhoneNumber);
        }

        model.addAttribute("allOrganizations", allOrganizations);
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        violations.clear();
        model.addAttribute("errors", violations);

        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Model model, Organization organization, HttpServletRequest request) {
        String name = request.getParameter("name");

        String phoneFormatted = request.getParameter("phoneNum");
        String phoneUnformatted = phoneFormatted.replaceAll("[^\\d.]", "");

        String description = request.getParameter("description");

        String[] superheroIds = request.getParameterValues("superheroId");

        List<Superhero> superheroes = new ArrayList<>();
        if (superheroIds != null) {

            for (String superheroId : superheroIds) {
                superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
            }
        } else {
            superheroes = null;
        }

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
            String locationDescription = request.getParameter("locationDescription");
            String longitude = request.getParameter("longitude");
            String latitude = request.getParameter("latitude");

            location.setLocationName(locationName);
            location.setAddress(address);
            location.setCity(city);
            location.setState(state);
            location.setZip(zip);
            location.setDescription(locationDescription);
            location.setLongitude(longitude);
            location.setLatitude(latitude);

//            locationDao.addLocation(location);
        }

        organization.setOrgName(name);
        organization.setPhoneNumber(phoneUnformatted);
        organization.setLocation(location);
        organization.setDescription(description);
        organization.setListOfSuperheroes(superheroes);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);

        if (violations.isEmpty()) {
            if (organization.getLocation().getLocationId() == 0) {
                location = locationDao.addLocation(location);
            }
            organizationDao.addOrganization(organization);
            return "redirect:/organizations";
        } else {
            List<Organization> allOrganizations = organizationDao.getAllOrganizations();
            List<Location> locations = locationDao.getAllLocations();
            List<Superhero> allSuperheroes = superheroDao.getAllSuperheros();

            for (Organization org : allOrganizations) {

                String unformattedPhoneNumber = org.getPhoneNumber();
                String formattedPhoneNumber = "("
                        + unformattedPhoneNumber.substring(0, 3)
                        + ") "
                        + unformattedPhoneNumber.substring(3, 6)
                        + "-"
                        + unformattedPhoneNumber.substring(6, 10);
                org.setPhoneNumber(formattedPhoneNumber);
            }

            model.addAttribute("allOrganizations", allOrganizations);
            model.addAttribute("locations", locations);
            model.addAttribute("superheroes", allSuperheroes);
            model.addAttribute("errors", violations);
            return "organizations";
        }

//        organizationDao.addOrganization(organization);
//        return "redirect:/organizations";
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
        String formattedPhoneNumber = "("
                + unformattedPhoneNumber.substring(0, 3)
                + ") "
                + unformattedPhoneNumber.substring(3, 6)
                + "-"
                + unformattedPhoneNumber.substring(6, 10);
        organization.setPhoneNumber(formattedPhoneNumber);

        model.addAttribute("organization", organization);
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("formattedPhoneNumber", formattedPhoneNumber);

        violationsEdit.clear();
        model.addAttribute("errors", violationsEdit);

        return "organizationEdit";
    }

    @PostMapping(path = "organizationEdit", consumes = "application/x-www-form-urlencoded")
    public String performOrganizationEdit(Model model, HttpServletRequest request, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("cancel")) {
            return "redirect:/organizations";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);
        String name = request.getParameter("name");
        String phoneFormatted = request.getParameter("phoneNum");
        String phoneUnformatted = "";
        if (!phoneFormatted.equals("")) {
            phoneUnformatted = phoneFormatted.replaceAll("[^\\d.]", "");
        }

        String locationId = request.getParameter("locationId");
        String description = request.getParameter("description");
        String[] superheroIds = request.getParameterValues("superheroId");

        organization.setOrgName(name);
        organization.setPhoneNumber(phoneUnformatted);
        organization.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        organization.setDescription(description);

        List<Superhero> superheroes = new ArrayList<>();
        if (superheroIds != null) {
            for (String superheroId : superheroIds) {
                superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
            }
        } else {
            superheroes = null;
        }
        organization.setListOfSuperheroes(superheroes);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsEdit = validate.validate(organization);

        if (violationsEdit.isEmpty()) {
            organizationDao.updateOrganization(organization);
            return "redirect:/organizationDetails?id=" + organization.getOrgId();

        } else {
            List<Location> locations = locationDao.getAllLocations();
            List<Superhero> allSuperheroes = superheroDao.getAllSuperheros();

            String unformattedPhoneNumber = organization.getPhoneNumber();
            String formattedPhoneNumber = "";
            if (unformattedPhoneNumber.length() == 10) {
                formattedPhoneNumber = "("
                        + unformattedPhoneNumber.substring(0, 3)
                        + ") "
                        + unformattedPhoneNumber.substring(3, 6)
                        + "-"F
                        + unformattedPhoneNumber.substring(6, 10);
            }
            organization.setPhoneNumber(formattedPhoneNumber);
            if (organization.getListOfSuperheroes() == null) {
                organization.setListOfSuperheroes(new ArrayList<Superhero>());
            }

            model.addAttribute("locations", locations);
            model.addAttribute("superheroes", allSuperheroes);
            model.addAttribute("formattedPhoneNumber", formattedPhoneNumber);
            model.addAttribute("organization", organization);

            model.addAttribute("errors", violationsEdit);
            return "organizationEdit";
        }

    }
}
