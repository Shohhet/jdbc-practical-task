package com.shoggoth.pojo;

public class Specialty {
    private long id;
    private String name;

     public Specialty(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Specialty() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Specialty{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
