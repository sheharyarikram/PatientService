CREATE TABLE patient (
  id			INTEGER AUTO_INCREMENT PRIMARY KEY,
  email 		VARCHAR(255) NOT NULL UNIQUE,
  first_name	VARCHAR(255) NOT NULL,
  last_name 	VARCHAR(255) NOT NULL,
  birth_date 	DATE NOT NULL,
  sex 			VARCHAR(255) NOT NULL
  );
  