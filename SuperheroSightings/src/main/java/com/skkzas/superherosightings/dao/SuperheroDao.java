/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Superhero;
import java.util.List;

/**
 *
 * @author kristinazakharova
 */
public interface SuperheroDao {
    
    public Superhero getSuperheroById(int id);
    
    public List<Superhero> getAllSuperheros();
    
    public Superhero addSuperhero(Superhero Superhero);
    
    public void updateSuperhero(Superhero Superhero);
    
    public void deleteSuperheroById(int id);
    
    //getAllSuperherosForOrganization ???
  
}
