package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.DevicesMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.TraineeMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Devices;
import com.example.projectpartbprogram_group39.Models.Trainee;

import java.io.IOException;
import java.util.List;

public class settingController {

    private static final DaoInterface<Trainee> traineeDao = new DaoImplement<>("userInfo.txt", new TraineeMapper());
    private static final DaoInterface<Devices> deviceDao = new DaoImplement<>("devices.txt", new DevicesMapper());

    public List<Devices> getAllDevices() throws IOException {
        return deviceDao.getAll();
    }

    public void initializeDevices(List<Devices> devicesList) throws IOException {
        if (devicesList.isEmpty()) {
            deviceDao.add(new Devices("Apple Watch Series 9", false, true));
            deviceDao.add(new Devices("Apple Watch Ultra 2", false, true));
            deviceDao.add(new Devices("Apple Watch Series 10", false, false));
        }
    }

    public void updateDeviceStatus(Devices selectedDevice, List<Devices> devicesList) throws IOException {
        boolean newStatus = !selectedDevice.getIsConnected();
        selectedDevice.setIsConnected(newStatus);

        for (Devices device : devicesList) {
            if (!device.equals(selectedDevice)) {
                device.setIsConnected(false);
            }
            deviceDao.update(device, device);
        }

        deviceDao.update(selectedDevice, selectedDevice);
    }

    public void updateDeviceAddedStatus(Devices device) throws IOException {
        boolean newStatus = !device.isAdded();
        device.setAdded(newStatus);

        if (!newStatus) {
            device.setIsConnected(false);
        }

        deviceDao.update(device, device);
    }

    public void deleteCurrentTrainee(Trainee curTrainee) throws IOException {
        traineeDao.delete(curTrainee);
    }

}
