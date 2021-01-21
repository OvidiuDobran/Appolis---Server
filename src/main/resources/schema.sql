USE appolis;

DROP TABLE problem_history;
DROP TABLE problem;
DROP TABLE problem_status;
DROP TABLE problem_category;
DROP TABLE clerk;
DROP TABLE citizen;
DROP TABLE city;
DROP TABLE country;
DROP TABLE hibernate_sequence;

CREATE TABLE country (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE city (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country_id INT,
    CONSTRAINT fk_city_country FOREIGN KEY (country_id)
        REFERENCES country (id)
);

CREATE TABLE citizen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE clerk (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    city_id INT,
    CONSTRAINT fk_clerk_city FOREIGN KEY (city_id)
        REFERENCES city (id)
);

CREATE TABLE problem_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE problem_status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE problem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    CONSTRAINT fk_problem_category FOREIGN KEY (category_id)
        REFERENCES problem_category (id),
        city_id INT,
    CONSTRAINT fk_problem_city FOREIGN KEY (city_id)
        REFERENCES city (id),
    description TEXT,
    latitude DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL,
    citizen_id INT,
    CONSTRAINT fk_problem_citizen FOREIGN KEY (citizen_id)
        REFERENCES citizen (id)
);

CREATE TABLE problem_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    problem_id INT,
    CONSTRAINT fk_problemhistory_problem FOREIGN KEY (problem_id)
        REFERENCES problem (id),
    creation_date TIMESTAMP NOT NULL,
    comment TEXT,
    status_id INT,
    CONSTRAINT fk_problemhistory_status FOREIGN KEY (status_id)
        REFERENCES problem_status (id),
    clerk_id INT,
    CONSTRAINT fk_problemhistory_clerk FOREIGN KEY (clerk_id)
        REFERENCES clerk (id)
);