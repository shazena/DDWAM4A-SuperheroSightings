package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Organization;
import com.skkzas.superherosightings.dto.Superhero;
import java.util.List;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 29, 2020
 */
public interface OrganizationDao {

    public Organization getOrganizationById(int id);

    public List<Organization> getAllOrganizations();

    public Organization addOrganization(Organization organization);

    public void updateOrganization(Organization organization);

    public void deleteOrganizationById(int id);

    public List<Organization> getOrganizationsForSuperhero(Superhero superhero);

}
