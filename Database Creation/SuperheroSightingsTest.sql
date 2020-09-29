DROP DATABASE IF EXISTS SuperheroSightingsDBTest;

CREATE DATABASE SuperheroSightingsDBTest;

USE SuperheroSightingsDBTest;

CREATE TABLE Power (
    PowerId INT PRIMARY KEY AUTO_INCREMENT,
    PowerName VARCHAR(50) NOT NULL
);

CREATE TABLE Superhero (
    SuperheroId INT PRIMARY KEY AUTO_INCREMENT,
    SuperheroName VARCHAR(50) NOT NULL,
    `Description` VARCHAR(255) NOT NULL,
    PowerId INT NOT NULL,
    CONSTRAINT fk_Superhero_Power FOREIGN KEY (PowerId)
        REFERENCES Power (PowerId)
);

CREATE TABLE Location (
    LocationId INT PRIMARY KEY AUTO_INCREMENT,
    LocationName VARCHAR(50) NOT NULL,
    `Description` VARCHAR(255) NOT NULL,
    Address VARCHAR(50) NOT NULL,
    City VARCHAR(50) NOT NULL,
    State CHAR(2) NOT NULL,
    Zip CHAR(5) NOT NULL,
    Latitude DECIMAL(8 , 6 ) NOT NULL,
    Longitude DECIMAL(9 , 6 ) NOT NULL
);

CREATE TABLE `Organization` (
    OrgId INT PRIMARY KEY AUTO_INCREMENT,
    OrgName VARCHAR(50) NOT NULL,
    `Description` VARCHAR(255) NOT NULL,
    PhoneNumber CHAR(10) NOT NULL,
    LocationId INT NOT NULL,
    CONSTRAINT fk_Organization_Location FOREIGN KEY (LocationID)
        REFERENCES Location (LocationID)
);

CREATE TABLE SuperheroOrganization (
    SuperheroId INT,
    OrgId INT,
    PRIMARY KEY pk_SuperheroOrganization (SuperheroId , OrgId),
    CONSTRAINT fk_SuperheroOrganization_Superhero FOREIGN KEY (SuperheroId)
        REFERENCES Superhero (SuperheroId),
    CONSTRAINT fk_SuperheroOrganization_Organization FOREIGN KEY (OrgId)
        REFERENCES `Organization` (OrgId)
);

CREATE TABLE Sighting (
    SightingID INT PRIMARY KEY AUTO_INCREMENT,
    `Date` DATE NOT NULL,
    LocationId INT NOT NULL,
    SuperheroId INT NOT NULL,
    CONSTRAINT fk_Sighting_Location FOREIGN KEY (LocationId)
        REFERENCES Location (LocationId),
    CONSTRAINT fk_Sighting_Superhero FOREIGN KEY (SuperheroId)
        REFERENCES Superhero (SuperheroId)
);
