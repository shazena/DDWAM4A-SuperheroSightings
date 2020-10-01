package com.skkzas.superherosightings.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 25, 2020
 */
public class Organization {

    private int orgId;
    private String orgName;
    private String description;
    private String phoneNumber;
    private Location location;
    private List<Superhero> listOfSuperheroes = new ArrayList<>();

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Superhero> getListOfSuperheroes() {
        return listOfSuperheroes;
    }

    public void setListOfSuperheroes(List<Superhero> listOfSuperheroes) {
        this.listOfSuperheroes = listOfSuperheroes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.orgId;
        hash = 97 * hash + Objects.hashCode(this.orgName);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.phoneNumber);
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Objects.hashCode(this.listOfSuperheroes);
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
        final Organization other = (Organization) obj;
        if (this.orgId != other.orgId) {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.listOfSuperheroes, other.listOfSuperheroes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organization{" + "orgId=" + orgId + ", orgName=" + orgName + ", description=" + description + ", phoneNumber=" + phoneNumber + ", location=" + location + ", listOfSuperheroes=" + listOfSuperheroes + '}';
    }

}
