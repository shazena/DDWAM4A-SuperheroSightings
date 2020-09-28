package com.skkzas.superherosightings.dao;

import com.skkzas.superherosightings.dto.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 25, 2020
 */
@Repository
public class OrganizationDaoDB {

    @Autowired
    JdbcTemplate jdbc;

//    private int orgId;
//    private String orgName;
//    private String description;
//    private String phoneNumber;
//    private Location location;
//    private ArrayList<Superhero> listOfSuperheroes = new ArrayList<>();
    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrgId(rs.getInt("OrgId"));
            organization.setOrgName(rs.getString("OrgName"));
            organization.setDescription(rs.getString("Description"));
            organization.setPhoneNumber(rs.getString("PhoneNumber"));

            return organization;
        }
    }
}
