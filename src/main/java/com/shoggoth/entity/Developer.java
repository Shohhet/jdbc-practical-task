package com.shoggoth.entity;

import java.util.List;
import java.util.Objects;

public class Developer extends Entity{
    private String firstName;
    private String lastName;
    List<Skill> skills;
    Specialty specialty;

    public Developer() {}

    public Developer(long id, String firstName, String lastName, List<Skill> skills, Specialty specialty, Status status ) {
        super(id, status);
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.specialty = specialty;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Developer developer)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(firstName, developer.firstName) &&
               Objects.equals(lastName, developer.lastName) &&
               Objects.equals(skills, developer.skills) &&
               Objects.equals(specialty, developer.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, skills, specialty);
    }
}
