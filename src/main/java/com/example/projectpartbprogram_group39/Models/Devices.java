package com.example.projectpartbprogram_group39.Models;

public class Devices {
    String devicesName;
    boolean isConnected;
    boolean isAdded;

    public Devices(String devicesName, boolean isConnected, boolean isAdded) {
        this.devicesName = devicesName;
        this.isConnected = isConnected;
        this.isAdded = isAdded;
    }

    public String getDevicesName() {
        return devicesName;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected){
        this.isConnected = isConnected;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    @Override
    public String toString() {
        return devicesName + "/" + isConnected + "/" + isAdded;
    }

    public static Devices splitString(String line){
        String[] parts = line.split("/");
        return new Devices(parts[0], Boolean.parseBoolean(parts[1]),Boolean.parseBoolean(parts[2]));
    }
}
