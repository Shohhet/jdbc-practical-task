USE jdbc_practical_task_db;
CREATE TABLE specialty (
                           id INTEGER PRIMARY KEY AUTO_INCREMENT,
                           specialty_name varchar(128) UNIQUE NOT NULL
);

CREATE TABLE skill (
                       id INTEGER PRIMARY KEY AUTO_INCREMENT,
                       skill_name varchar(128) UNIQUE NOT NULL
);

CREATE TABLE developer (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           first_name varchar(128) NOT NULL,
                           last_name varchar(128) NOT NULL,
                           specialty_id INTEGER,
                           status_id INTEGER
);

CREATE TABLE developers_skills (
                                   developer_id bigint,
                                   skill_id INTEGER
);

CREATE TABLE status (
                        id INTEGER PRIMARY KEY AUTO_INCREMENT,
                        status_name varchar(128) UNIQUE NOT NULL
);

ALTER TABLE developer ADD FOREIGN KEY (specialty_id) REFERENCES specialty (id);

ALTER TABLE developer ADD FOREIGN KEY (status_id) REFERENCES status (id);

ALTER TABLE developers_skills ADD FOREIGN KEY (developer_id) REFERENCES developer (id);

ALTER TABLE developers_skills ADD FOREIGN KEY (skill_id) REFERENCES skill (id);
