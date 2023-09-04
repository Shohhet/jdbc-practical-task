package com.shoggoth.crudapp.controller.command;

public enum CommandType {
    ADD_SPECIALTY("add_specialty"),
    GET_SPECIALTY("get_specialty"),
    GET_ALL_SPECIALTYS("get_all_specialtys"),
    UPDATE_SPECIALTY("update_specialty"),
    DELETE_SPECIALTY("delete_specialty"),
    ADD_SKILL("add_skill"),
    GET_SKILL("get_skill"),
    GET_ALL_SKILLS("get_all_skills"),
    UPDATE_SKILL("update_skill"),
    DELETE_SKILL("delete_skill"),
    ADD_DEVELOPER("add_developer"),
    GET_DEVELOPER("get_developer"),
    GET_ALL_DEVELOPERS("get_all_developers"),
    UPDATE_DEVELOPER("update_developer"),
    DELETE_DEVELOPER("delete_developer"),
    EXIT("exit"),
    HELP("help");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
