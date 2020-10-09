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
public class Power {

    private int powerId;
    @NotBlank(message = "Power name must not be empty.")
    @Size(max = 50, message = "Power name must be less than 50 characters.")
    private String powerName;

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.powerId;
        hash = 97 * hash + Objects.hashCode(this.powerName);
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
        final Power other = (Power) obj;
        if (this.powerId != other.powerId) {
            return false;
        }
        if (!Objects.equals(this.powerName, other.powerName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Power{" + "powerId=" + powerId + ", powerName=" + powerName + '}';
    }

}
