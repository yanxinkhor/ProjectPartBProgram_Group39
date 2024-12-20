package com.example.projectpartbprogram_group39.DAO.ClassMapper;

import com.example.projectpartbprogram_group39.Models.Devices;

public class DevicesMapper implements EntityMapper<Devices>{
    @Override
    public Devices fromString(String string) {
        return Devices.splitString(string);
    }

    @Override
    public String toString(Devices object) {
        return object.toString();
    }

    @Override
    public boolean equals(Devices obj1, Devices obj2) {
        return obj1.getDevicesName().equals(obj2.getDevicesName());
    }
}
