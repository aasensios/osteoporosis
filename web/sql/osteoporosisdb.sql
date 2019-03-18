/**
 * This is a MySQL script to create the database osteoporosis to practice how to manage databases with JSP/JDBC.
 * It has to be run in phpmyadmin by the root user (or other user with all privileges on database).
 * Author: Alejandro Asensio
 * Date: 2019-03-07
 */

-- Previous drops

DROP DATABASE IF EXISTS osteoporosis;
-- DROP USER IF EXISTS 'provenuser'@'localhost';

-- Database creation and usage

CREATE DATABASE osteoporosis DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE osteoporosis;

-- User creation and privileges

CREATE USER osteoporosis IDENTIFIED BY 'Osteoporosis1.';
GRANT ALL PRIVILEGES ON osteoporosis.* TO osteoporosis;

-- Table patients

CREATE TABLE patients
(
    registerId INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    age INT(5) DEFAULT NULL,
    ageGroup VARCHAR(50) DEFAULT NULL,
    weight INT(11) DEFAULT NULL,
    height INT(11) DEFAULT NULL,
    imc DECIMAL(10,2) DEFAULT NULL,
    classification VARCHAR(50) DEFAULT NULL,
    menarche INT(5) DEFAULT NULL,
    menopause BOOLEAN DEFAULT NULL,
    menopauseType VARCHAR(50) DEFAULT NULL
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- INSERT INTO patients (age, ageGroup, weight, height, imc, classification, menarche, menopause, menopauseType) VALUES (57,'55-59',70,168,24.80,'OSTEOPENIA',12,0,'NO CONSTA');
-- INSERT INTO patients (age, ageGroup, weight, height, imc, classification, menarche, menopause, menopauseType) VALUES (46,'45-49',53,152,22.94,'OSTEOPENIA',13,0,'NO CONSTA');
-- INSERT INTO patients (age, ageGroup, weight, height, imc, classification, menarche, menopause, menopauseType) VALUES (45,'45-49',64,158,25.64,'NORMAL',14,0,'NO CONSTA');
-- INSERT INTO patients (age, ageGroup, weight, height, imc, classification, menarche, menopause, menopauseType) VALUES (53,'50-54',78,161,30.09,'OSTEOPENIA',10,1,'NATURAL');
-- INSERT INTO patients (age, ageGroup, weight, height, imc, classification, menarche, menopause, menopauseType) VALUES (46,'45-49',56,157,22.72,'NORMAL',13,0,'NO CONSTA');
-- INSERT INTO patients (age, ageGroup, weight, height, imc, classification, menarche, menopause, menopauseType) VALUES (45,'45-49',63,170,21.97,'OSTEOPOROSI',14,0,'NO CONSTA');
INSERT INTO patients (age, ageGroup, weight, height, imc, classification, menarche, menopause, menopauseType) VALUES 
(57,'55-59',70,168,24.80,'OSTEOPENIA',12,0,'NO CONSTA'),
(46,'45-49',53,152,22.94,'OSTEOPENIA',13,0,'NO CONSTA'),
(45,'45-49',64,158,25.64,'NORMAL',14,0,'NO CONSTA'),
(53,'50-54',78,161,30.09,'OSTEOPENIA',10,1,'NATURAL'),
(46,'45-49',56,157,22.72,'NORMAL',13,0,'NO CONSTA'),
(45,'45-49',63,170,21.97,'OSTEOPOROSI',14,0,'NO CONSTA');

-- Table users

CREATE TABLE users
(
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(50) DEFAULT 'basic'
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users (username, password, role) VALUES
('lluisp','123456','admin'),
('mariagomez','mgomez','basic'),
('danimp','s2cr2','basic'),
('daina2001','d13n1','admin'),
('jonathangarcia','654321','basic');
