package com.skkzas.superherosightings.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Sep 25, 2020
 */
@Repository
public class PowerDaoDB {

    @Autowired
    JdbcTemplate jdbc;
}
