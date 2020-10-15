SELECT * FROM superherosightingsdb.power;
SELECT * FROM superherosightingsdb.superhero;
SELECT * FROM superherosightingsdb.location;
SELECT * FROM superherosightingsdb.organization;
SELECT * FROM superherosightingsdb.superheroorganization;
SELECT * FROM superherosightingsdb.sighting;

SELECT * FROM superherosightingsdbtest.power;
SELECT * FROM superherosightingsdbtest.superhero;
SELECT * FROM superherosightingsdbtest.location;
SELECT * FROM superherosightingsdbtest.organization;
SELECT * FROM superherosightingsdbtest.superheroorganization;
SELECT * FROM superherosightingsdbtest.sighting;
desc location;


DELETE FROM power 
WHERE
    powerId = 16;
delete from superheroorganization where SuperheroId = 1;
DELETE FROM superhero where SuperheroId = 1;

select * from superhero where powerid = 5;
delete from sighting where superheroId = 5;
delete from sighting where superheroId = 6;
delete from superheroorganization where superheroId = 5;
delete from superheroorganization where superheroId = 6;
DELETE from superhero where powerId = 5;
DELETE from power where powerId = 5;

DELETE FROM Sighting WHERE SightingID = 1;

delete from sighting where LocationId = 1;
delete from Location where locationId = 1;



delete from sighting where LocationId = 1;
select * from organization where LocationId = 1;
DELETE from superheroorganization where OrgId = 7;
DELETE from superheroorganization where OrgId = 8;
delete from organization where LocationId = 1;
delete from Location where locationId = 1;


SELECT 
    *
FROM
    Location l
INNER JOIN Sighting si ON si.locationid = l.locationId
INNER JOIN Superhero su ON su.superheroId = si.superheroId
WHERE su.superheroId = 5;

SELECT * from Sighting si 
inner join superhero su ON si.superheroId = su.superheroId
where date = "2020-08-12";


SELECT 
    *
FROM
    Superhero s
        JOIN
    Sighting si ON s.SuperheroId = si.SuperheroId
        JOIN
    Location l ON si.LocationId = l.LocationId
WHERE
    l.LocationId = 2;
    
desc organization;


SELECT LAST_INSERT_ID();

SELECT 
    *
FROM
    Location l
        JOIN
    Sighting s ON l.locationId = s.sightingid
WHERE
    SightingId = 2;
    
    
    SELECT 
    *
FROM
    Superhero su
        JOIN
    Power p ON su.PowerId = p.PowerId
WHERE
su.PowerId = 2;

SELECT 
    *
FROM
    Location l
        INNER JOIN
    Sighting si ON si.locationid = l.locationId
        INNER JOIN
    Superhero su ON su.superheroId = si.superheroId
WHERE
    su.superheroId = 2;
    
    
    SELECT 
    *
FROM
    Sighting si
        INNER JOIN
    superhero su ON si.superheroId = su.superheroId
WHERE
    si.date = '2020-08-12';
    
    
    
    SELECT 
    *
FROM
    Superhero su
        JOIN
    Sighting si ON su.SuperheroId = si.SuperheroId
        JOIN
    Location l ON si.LocationId = l.LocationId
    
WHERE
    l.LocationId = 3
    group by su.superheroID;oh no
    
    
