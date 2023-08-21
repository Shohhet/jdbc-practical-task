package com.shoggoth.entity;

import java.util.Objects;

public abstract class Entity {
    private long id;
    private Status status;

    public Entity() {
    }

    public Entity(long id, Status status) {
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
        if (!(o instanceof Entity entity)) return false;
        return id == entity.id && status == entity.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }
}
