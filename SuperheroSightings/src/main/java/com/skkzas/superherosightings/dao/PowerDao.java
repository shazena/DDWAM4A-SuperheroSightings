/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Power;
import java.util.List;

/**
 *
 * @author kristinazakharova
 */
public interface PowerDao {
    
    public Power getPowerById(int id);
    
    public List<Power> getAllPowers();
    
    public Power addPower(Power power);
    
    public void updatePower(Power power);
    
    public void deletePowerById(int id);
    
}
