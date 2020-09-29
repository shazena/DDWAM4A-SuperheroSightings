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

INSERT INTO Location(LocationName, description, address, city