package com.example.projectpartbprogram_group39.Models;


public class Coach {
    private String name;
    private boolean isJoined;

    public Coach(String name, boolean isJoined) {
        this.name = name;
        this.isJoined = isJoined;
    }

    public String getName() { return name; }
    public boolean getIsJoined(){return isJoined; }
    public void setIsJoined(boolean isJoined) { this.isJoined = isJoined; }

    @Override
    public String toString() {
        return name + "," + isJoined;
    }

    public static Coach splitString(String str) {
        String[] parts = str.split(",");
        return new Coach(parts[0], Boolean.parseBoolean(parts[1]));
    }

}
