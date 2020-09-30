USE SuperheroSightingsDB;

INSERT INTO Power(powerName) VALUES 
("Shrink to Subatomic Size"),
("Cold-Gun to freeze objects"),
("Telepathy"),
("Extensive knowledge of the Occult"),
("Flight"),
("Genius Intellect");

INSERT INTO Superhero(SuperheroName, description, PowerId) VALUES
("Atom", "Physicist who can shrink to the size of subatomic particles", 1),
("Captain Cold", "Master thief and marksman with a cold-gun that can freeze objects to absolute zero", 2),
("Gorilla Grodd", "Evil, super-intelligent gorilla who gained mental powers after being exposed to a strange meteorite's radiation",3),
("John Constantine", "Cynical working class warlock, occult detective, and con man from Liverpool", 4),
("Wonder Woman", "Amazonian Warrior Princess with superhuman powers gifted by the Greek Gods", 5),
("Superman", "Kryptonian with abilities superior to all humans", 5),
("Batman", "Genius, Billionaire, Playboy, Philanthropist", 6);

INSERT INTO Location(LocationName, `description`, address, city, state, zip, latitude, longitude) VALUES
("Cobblestone Ale House","Bar","400 1st St","Liverpool","NY","13088",43.102444,-76.208779),
("The International Museum of World War II","Museum","8 Mercer Rd","Natick","MA","01760",42.296422,-71.387846),
("Barnes and Noble", "Bookstore", "267 7th Ave","Brooklyn","NY","11215",40.668829, -73.979773),
("Go Get Em Tiger Coffee Shop","Coffee Shop","230 N Larchmont Blvd","Los Angeles","CA","90004",34.075585,-118.323465),
("Shenanigans Night Club","Night Club","820 34th St N","Texas City","TX","77590",29.391451,-94.946394),
("Connecticut Air & Space Center","Space Center","550 Main St","Stratford","CT","06615",41.17118,-73.125349),
("Labcorp","Lab", "406 Carriage Dr","Beckley","WV", "25801", 37.787906,-81.199006);

INSERT INTO Organization(OrgName, Description, PhoneNumber, LocationId) VALUES 
("Legends of Tomorrow", "Time Travelers prepared for every anachronism","1111111111",7),
("Justice League","Earth’s first line of defense against threats too large for humanity to face alone","2222222222",4),
("Teen Titans", "Teenage heroes who keep the world safe from the clutches of evil", "3333333333", 5),
("Injustice League", "Dedicated to destruction, dominating the world, and defeating the Justice League", "4444444444", 6),
("Suicide Squad", "Group of super-villains who have license to take drastic action in the name of the mission", "5555555555", 6),
("Justice League Dark", "Branch of the Justice League dedicated to dealing with mystical and supernatural threats", "6666666666", 4),
("Secret Society of Super Villains", "Villains whose goal is to remove 10 of the Justice League and Justice Society members to offset the Multi-verse", "7777777777", 6),
("Legion of Doom", "Villains who, under Grodd, wanted to rewrite human DNA into becoming Gorillas, and under Luthor, wanted to reconstitute Braniac to fight the Justice League", "8888888888", 6);

INSERT INTO superheroorganization(superheroId, orgId) VALUES 
(1,1),
(1,2),
(1,3),
(2,1),
(2,2),
(2,4),
(2,5),
(2,8),
(2,7),
(3,4),
(3,8),
(3,7),
(4,1),
(4,6),
(5,6),
(5,2),
(6,2),
(7,2);

INSERT INTO sighting(date, locationId, superheroId) VALUES 
("2020-08-12", 1, 4),
("2020-08-12", 1, 2),
("2020-07-02", 2, 1),
("2020-07-02", 2, 5),
("2020-01-15", 3, 3);
