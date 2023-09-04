package com.shoggoth.crudapp.model.entity;

import java.util.List;
import java.util.Objects;

public class DeveloperEntity extends BaseEntity {
    private String firstName;
    private String lastName;
    private List<SkillEntity> skills;
    private SpecialtyEntity specialty;

    public DeveloperEntity() {}

    public DeveloperEntity(long id, String firstName, String lastName, List<SkillEntity> skills, SpecialtyEntity specialty, Status status ) {
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

    public List<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillEntity> skillEntities) {
        this.skills = skillEntities;
    }

    public SpecialtyEntity getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyEntity specialtyEntity) {
        this.specialty = specialtyEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeveloperEntity developerEntity)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(firstName, developerEntity.firstName) &&
               Objects.equals(lastName, developerEntity.lastName) &&
               Objects.equals(skills, developerEntity.skills) &&
               Objects.equals(specialty, developerEntity.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, skills, specialty);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Developer {")
                .append("\n")
                .append("id = ").append(this.getId()).append("\n")
                .append("firstName = ").append(firstName).append("\n")
                .append("lastName = ").append(lastName).append("\n")
                .append("skills = ").append(skills).append("\n")
                .append("specialty = ").append(specialty).append("\n")
                .append("status = ").append(this.getStatus()).append("\n")
                .append("}")
                .toString();
    }
}
