package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Location;
import com.skkzas.superherosightings.dto.Superhero;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Sep 28, 2020
 */
public interface LocationDao {

    public Location getLocationById(int id);

    public List<Location> getAllLocations();

    public Location addLocation(Location location);

    public void updateLocation(Location location);

    public void deleteLocationById(int id);

    public List<Location> getAllLocationsForSuperhero(Superhero superhero);

}
