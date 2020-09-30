/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Organization;
import com.skkzas.superherosightings.dto.Superhero;
import java.util.List;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 25, 2020
 */
public interface SuperheroDao {

    public Superhero getSuperheroById(int id);

    public List<Superhero> getAllSuperheros();

    public Superhero addSuperhero(Superhero superhero);

    public void updateSuperhero(Superhero superhero);

    public void deleteSuperheroById(int id);

    public List<Superhero> getAllSuperheroesForLocation(Location location);

    public List<Superhero> getAllSuperherosForOrganization(Organization organization);

}
