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
public class Location {

    private int locationId;
    @NotBlank(message = "Location name must not be empty.")
    @Size(max = 50, message = "Location name must be at most 50 characters.")
    private String locationName;
    @NotBlank(message = "Location description must not be empty.")
    @Size(max = 255, message = "Location description must be at most 255 characters.")
    private String description;
    @NotBlank(message = "Location address must not be empty.")
    @Size(max = 50, message = "Location address must be at most 50 characters.")
    private String address;
    @NotBlank(message = "City must not be empty.")
    @Size(max = 50, message = "City must be at most 50 characters.")
    private String city;
    @NotBlank(message = "State must not be empty.")
    @Size(min = 2, max = 2, message = "State must contain 2 characters.")
    private String state;
    @NotBlank(message = "Zip must not be empty.")
    @Size(min = 5, max = 5, message = "Zip must contain 5 characters.")
    private String zip;
    @NotBlank(message = "Sorry, please try again")
    private String latitude;
    @NotBlank(message = "Sorry, please try again")
    private String longitude;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.locationId;
        hash = 71 * hash + Objects.hashCode(this.locationName);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.address);
        hash = 71 * hash + Objects.hashCode(this.city);
        hash = 71 * hash + Objects.hashCode(this.state);
        hash = 71 * hash + Objects.hashCode(this.zip);
        hash = 71 * hash + Objects.hashCode(this.latitude);
        hash = 71 * hash + Objects.hashCode(this.longitude);
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
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", locationName=" + locationName + ", description=" + description + ", address=" + address + ", city=" + city + ", state=" + state + ", zip=" + zip + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

}
