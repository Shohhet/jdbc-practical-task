package com.shoggoth.crudapp.model.entity;

import java.util.Objects;

public class SpecialtyEntity extends BaseEntity {
    private String name;

    public SpecialtyEntity() {
    }

    public SpecialtyEntity(long id, String name, Status status) {
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
        if (!(o instanceof SpecialtyEntity specialtyEntity)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, specialtyEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append("Specialty{")
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
