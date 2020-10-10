USE SuperheroSightingsDB;
ALTER TABLE superhero
ADD COLUMN photoFileName VARCHAR(255) AFTER powerId;