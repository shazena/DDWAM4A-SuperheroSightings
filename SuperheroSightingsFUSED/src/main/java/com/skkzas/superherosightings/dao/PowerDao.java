package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Power;
import java.util.List;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 */
public interface PowerDao {

    public Power getPowerById(int id);

    public List<Power> getAllPowers();

    public Power addPower(Power power);

    public void updatePower(Power power);

    public void deletePowerById(int id);

}
