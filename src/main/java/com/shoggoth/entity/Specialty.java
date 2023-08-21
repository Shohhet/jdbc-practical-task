package com.shoggoth.entity;

import java.util.Objects;

public class Specialty extends Entity {
    private String name;

    public Specialty() {
    }

    public Specialty(long id, String name, Status status) {
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
        if (!(o instanceof Specialty specialty)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, specialty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
