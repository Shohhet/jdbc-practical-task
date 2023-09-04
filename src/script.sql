DROP DATABASE IF EXISTS jdbc_practical_task_db;
CREATE DATABASE jdbc_practical_task_db;
USE jdbc_practical_task_db;

CREATE TABLE specialtyEntity (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           specialty_name VARCHAR(128) UNIQUE NOT NULL,
                           specialty_status ENUM ('ACTIVE', 'DELETED')
);

CREATE TABLE skillEntity (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       skill_name VARCHAR(128) UNIQUE NOT NULL,
                       skill_status ENUM ('ACTIVE', 'DELETED')
);

CREATE TABLE developerEntity (
                           id bigint PRIMARY KEY AUTO_INCREMENT,
                           first_name VARCHAR(128) NOT NULL,
                           last_name VARCHAR(128) NOT NULL,
                           specialty_id BIGINT,
                           developer_status ENUM ('ACTIVE', 'DELETED')
);

CREATE TABLE developer_skill (
                                  developer_id BIGINT,
                                  skill_id BIGINT,
                                  developer_skill_status ENUM ('ACTIVE', 'DELETED')
);

ALTER TABLE developerEntity ADD FOREIGN KEY (specialty_id) REFERENCES specialtyEntity (id);

ALTER TABLE developer_skill ADD FOREIGN KEY (developer_id) REFERENCES developerEntity (id);

ALTER TABLE developer_skill ADD FOREIGN KEY (skill_id) REFERENCES skillEntity (id);
