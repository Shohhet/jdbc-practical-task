package com.shoggoth.crudapp.model.entity;

import java.util.Objects;

public abstract class BaseEntity {
    private long id;
    private Status status;

    BaseEntity() {
    }

    BaseEntity(long id, Status status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity baseEntity)) return false;
        return id == baseEntity.id && status == baseEntity.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

}
