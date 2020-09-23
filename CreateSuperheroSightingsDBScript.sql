DROP DATABASE IF EXISTS SuperheroSightingsDB;
CREATE DATABASE SuperheroSightingsDB;
USE SuperheroSightingsDB;

CREATE TABLE `Super` (

SuperName VARCHAR(50) NOT NULL PRIMARY KEY, 
`Description` VARCHAR(255) NOT NULL

);

CREATE TABLE Power (

PowerName VARCHAR(50) NOT NULL PRIMARY KEY

);


CREATE TABLE PowerSuper (

SuperName VARCHAR(50) NOT NULL,
PowerName VARCHAR(50) NOT NULL,
PRIMARY KEY pk_PowerSuper (SuperName, PowerName), 
	CONSTRAINT fk_PowerSuper_Super FOREIGN KEY (SuperName)
		REFERENCES Super(SuperName), 
	CONSTRAINT fk_PowerSuper_Power FOREIGN KEY (PowerName)
		REFERENCES Power (PowerName)

);

CREATE TABLE `Organization` (

OrgName VARCHAR(50) NOT NULL PRIMARY KEY,
`Description` VARCHAR(255) NOT NULL,
Address VARCHAR(50) NOT NULL,
City VARCHAR(50) NOT NULL,
State CHAR(2) NOT NULL, 
Zip CHAR(5) NOT NULL, 
PhoneNumber CHAR(10) NOT NULL

);

CREATE TABLE SuperOrganization (

SuperName VARCHAR(50) NOT NULL,
OrgName VARCHAR(50) NOT NULL,
PRIMARY KEY pk_SuperOrganization (SuperName, OrgName), 
	CONSTRAINT fk_SuperOrganization_Super FOREIGN KEY (SuperName)
		REFERENCES Super(SuperName), 
	CONSTRAINT fk_SuperOrganization_Power FOREIGN KEY (OrgName)
		REFERENCES `Organization` (OrgName)

);

CREATE TABLE Location (

LocationID INT PRIMARY KEY AUTO_INCREMENT, 
LocationName VARCHAR(50) NOT NULL,
Address VARCHAR(50) NOT NULL,
City VARCHAR(50) NOT NULL,
State CHAR(2) NOT NULL, 
Zip CHAR(5) NOT NULL, 
Latitude DECIMAL (9, 7) NOT NULL, 
Longitude DECIMAL (10, 7) NOT NULL, 
`Description` VARCHAR(255) NOT NULL

);

CREATE TABLE Sighting (

SightingID INT PRIMARY KEY AUTO_INCREMENT,
`Date` DATE NOT NULL, 
LocationID INT NOT NULL, 
SuperName VARCHAR(50) NOT NULL, 
CONSTRAINT fk_Sighting_Location FOREIGN KEY (LocationID)
    REFERENCES Location(LocationID), 
CONSTRAINT fk_Sighting_Super FOREIGN KEY (SuperName)
    REFERENCES `Super`(SuperName)
    
);
