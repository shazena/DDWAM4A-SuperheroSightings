package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Sighting;
import com.skkzas.superherosightings.dto.Superhero;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 29, 2020
 */
public interface SightingDao {

    public Sighting getSightingById(int id);

    public List<Sighting> getAllSightings();

    public List<Sighting> getLastTenSightings();

    public Sighting addSighting(Sighting sighting);

    public void updateSighting(Sighting sighting);

    public void deleteSightingById(int id);

    public List<Sighting> getAllSightingsForDate(LocalDate date);

    public List<Sighting> getAllSightingsForListOfSuperheros(List<Superhero> listOfSuperheroes);
}
