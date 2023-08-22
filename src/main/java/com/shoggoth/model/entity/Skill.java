package com.shoggoth.model.entity;

import java.util.Objects;

public class Skill extends Entity{
    private String name;

    public Skill(){}

    public Skill(long id, String name, Status status) {
        super(id, status);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill skill)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Skill{")
                .append(" id = ")
                .append(getId())
                .append(";")
                .append(" name = ")
                .append(name)
                .append(";")
                .append(" status = ")
                .append(getStatus())
                .append("}")
                .toString();
    }
}
