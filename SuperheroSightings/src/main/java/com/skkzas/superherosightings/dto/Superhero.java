package com.skkzas.superherosightings.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 25, 2020
 */
public class Superhero {

    private int superheroId;
    @NotBlank(message = "Superhero name must not be empty.")
    @Size(max = 50, message = "Superhero name must be at most 50 characters.")
    private String superheroName;
    @NotBlank(message = "Superhero description must not be empty.")
    @Size(max = 255, message = "Superhero description must be at most 255 characters.")
    private String superheroDescription;
    private Power power;

    public int getSuperheroId() {
        return superheroId;
    }

    public void setSuperheroId(int superheroId) {
        this.superheroId = superheroId;
    }

    public String getSuperheroName() {
        return superheroName;
    }

    public void setSuperheroName(String superheroName) {
        this.superheroName = superheroName;
    }

    public String getSuperheroDescription() {
        return superheroDescription;
    }

    public void setSuperheroDescription(String superheroDescription) {
        this.superheroDescription = superheroDescription;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.superheroId;
        hash = 13 * hash + Objects.hashCode(this.superheroName);
        hash = 13 * hash + Objects.hashCode(this.superheroDescription);
        hash = 13 * hash + Objects.hashCode(this.power);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Superhero other = (Superhero) obj;
        if (this.superheroId != other.superheroId) {
            return false;
        }
        if (!Objects.equals(this.superheroName, other.superheroName)) {
            return false;
        }
        if (!Objects.equals(this.superheroDescription, other.superheroDescription)) {
            return false;
        }
        if (!Objects.equals(this.power, other.power)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Superhero{" + "superheroId=" + superheroId + ", superheroName=" + superheroName + ", superheroDescription=" + superheroDescription + ", power=" + power + '}';
    }

}
