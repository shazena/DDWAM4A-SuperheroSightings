package com.skkzas.superherosightings.dao;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Shazena Khan, Kristina Zakharova, Arfin Shah
 *
 * Date Created: Oct 10, 2020
 */
public interface ImageDao {

    public String saveImage(MultipartFile file, String fileName, String directory);

    public String updateImage(MultipartFile file, String fileName, String directory);

    public boolean deleteImage(String fileName);
}
