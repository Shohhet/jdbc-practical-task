package com.shoggoth.pojo;

public class Skill {
    private long id;
    private String name;

    public Skill(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Skill() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Skill{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
