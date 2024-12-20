package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.ClassMapper.DevicesMapper;
import com.example.projectpartbprogram_group39.DAO.ClassMapper.TraineeMapper;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoImplement;
import com.example.projectpartbprogram_group39.DAO.genericDao.DaoInterface;
import com.example.projectpartbprogram_group39.Models.Devices;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class settingsController implements Initializable {
    @FXML
    private Pane opacityPane;

    @FXML
    private VBox deleteCtn;

    @FXML
    private AnchorPane DeviceCtn,myDevicePage,pairingPage,otherDevicePage;

    @FXML
    private HBox thirdDeviceCtn;

    @FXML
    private Button connectBtn1,connectBtn2,connectBtn3,addDeviceBtn;

    @FXML
    private Label deviceLbl,connectState;

    @FXML
    private ImageView watch1,watch2,watch3;

    private Timer timer;
    private static DaoInterface<Trainee> traineeDao = new DaoImplement<>("userInfo.txt", new TraineeMapper());
    private static DaoInterface<Devices> deviceDao = new DaoImplement<>("devices.txt",new DevicesMapper());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opacityPane.setVisible(false);

        Devices initDevice1 = new Devices("Apple Watch Series 9",false,true);
        Devices initDevice2 = new Devices("Apple Watch Ultra 2",false,true);
        Devices initDevice3 = new Devices("Apple Watch Series 10",false,false);
        try {
            List<Devices> devicesList = deviceDao.getAll();
            if(devicesList.isEmpty()){
                deviceDao.add(initDevice1);
                deviceDao.add(initDevice2);
                deviceDao.add(initDevice3);
            }
            updateButtonStates(devicesList);
            updateAddBtnState(devicesList);

            for (Devices device : devicesList) {
                if (device.getDevicesName().equals("Apple Watch Series 10")) {
                    thirdDeviceCtn.setVisible(device.isAdded());
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void connectDevicePU(ActionEvent e) {
        opacityPane.setVisible(true);
        DeviceCtn.setVisible(true);

    }

    public void connectDevice(ActionEvent e) {
        Button srcBtn = (Button) e.getSource();
        try {
            List<Devices> list = deviceDao.getAll();
            Devices selectedDevice = null;

            for (Devices device : list) {
                if (srcBtn == connectBtn1 && device.getDevicesName().equals("Apple Watch Series 9")) {
                    selectedDevice = device;
                } else if (srcBtn == connectBtn2 && device.getDevicesName().equals("Apple Watch Ultra 2")) {
                    selectedDevice = device;
                } else if(srcBtn == connectBtn3 && device.getDevicesName().equals("Apple Watch Series 10")){
                    selectedDevice = device;
                }
            }

            if (selectedDevice != null) {
                boolean newStatus = !selectedDevice.getIsConnected();
                selectedDevice.setIsConnected(newStatus);

                for (Devices device : list) {
                    if (!device.equals(selectedDevice)) {
                        device.setIsConnected(false);
                    }
                    deviceDao.update(device, device);
                }

                deviceDao.update(selectedDevice, selectedDevice);
            }

            updateButtonStates(list);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void updateButtonStates(List<Devices> devicesList) {
        boolean anyDeviceConnected = false;

        for (Devices device : devicesList) {
            if (device.getDevicesName().equals("Apple Watch Series 9")) {
                connectBtn1.setText(device.getIsConnected() ? "CONNECTED" : "NOT CONNECTED");
                connectBtn1.setStyle(device.getIsConnected() ? "-fx-text-fill:#a2cf73;" : "-fx-text-fill:#96a29f;");
                watch1.setVisible(device.getIsConnected());
                if (device.getIsConnected()) {
                    deviceLbl.setText("Apple Watch Series 9");
                    anyDeviceConnected = true;
                }
            } else if (device.getDevicesName().equals("Apple Watch Ultra 2")) {
                connectBtn2.setText(device.getIsConnected() ? "CONNECTED" : "NOT CONNECTED");
                connectBtn2.setStyle(device.getIsConnected() ? "-fx-text-fill:#a2cf73;" : "-fx-text-fill:#96a29f;");
                watch2.setVisible(device.getIsConnected());
                if (device.getIsConnected()) {
                    deviceLbl.setText("Apple Watch Ultra 2");
                    anyDeviceConnected = true;
                }
            } else if (device.getDevicesName().equals("Apple Watch Series 10")) {
                connectBtn3.setText(device.getIsConnected() ? "CONNECTED" : "NOT CONNECTED");
                connectBtn3.setStyle(device.getIsConnected() ? "-fx-text-fill:#a2cf73;" : "-fx-text-fill:#96a29f;");
                watch3.setVisible(device.getIsConnected());
                if (device.getIsConnected()) {
                    deviceLbl.setText("Apple Watch Series 10");
                    anyDeviceConnected = true;
                }
            }
        }

        connectState.setText(anyDeviceConnected ? "connected" : "disconnected");


        if (!anyDeviceConnected) {
            deviceLbl.setText("No Device Connected");
        }
    }

    public void pairDevice(ActionEvent e){
        myDevicePage.setVisible(false);
        pairingPage.setVisible(true);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    pairingPage.setVisible(false);
                    otherDevicePage.setVisible(true);
                });
            }
        }, 3000);
    }

    public void addDevice(ActionEvent e) throws IOException{
        List<Devices> devicesList = deviceDao.getAll();
        Devices thirdDevice = null;

        for (Devices device : devicesList) {
            if (device.getDevicesName().equals("Apple Watch Series 10")) {
                thirdDevice = device;
                break;
            }
        }

        if (thirdDevice != null) {
            boolean newStatus = !thirdDevice.isAdded();
            thirdDevice.setAdded(newStatus);

            if (!newStatus) {
                thirdDevice.setIsConnected(false);
            }

            deviceDao.update(thirdDevice, thirdDevice);

            updateAddBtnState(devicesList);

            thirdDeviceCtn.setVisible(newStatus);
            if (newStatus) {
                otherDevicePage.setVisible(false);
                myDevicePage.setVisible(true);
            }
        }
    }

    public void updateAddBtnState(List<Devices> devicesList){
        for (Devices device : devicesList){
            addDeviceBtn.setText(device.isAdded()?"ADDED":"ADD TO DEVICE");
            addDeviceBtn.setStyle(device.isAdded()? "-fx-text-fill:red;" : "-fx-text-fill:#96a29f;");
        }
    }


    public void cancelAddingDevice(ActionEvent e){
        otherDevicePage.setVisible(false);
        myDevicePage.setVisible(true);
    }

    public void closeDevice(ActionEvent e){
        DeviceCtn.setVisible(false);
        opacityPane.setVisible(false);
    }

    public void deleteAccountPU(ActionEvent e) {
        Trainee currentTrainee = TraineeSession.getInstance().getCurrentTrainee();
        if (currentTrainee != null) {
            if ("Admin".equals(currentTrainee.getUsername()) && "admin1234".equals(currentTrainee.getPassword())) {
                showAlert.alert(Alert.AlertType.ERROR, "You cannot delete the Admin account.");
                return;
            }
        }
        opacityPane.setVisible(true);
        deleteCtn.setVisible(true);

    }

    public void sendFeedbackPU(ActionEvent e) {
        opacityPane.setVisible(true);
        openPopup("/com/example/projectpartbprogram_group39/View/feedback-view.fxml");
    }

    private void openPopup(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);

            popupStage.setScene(new Scene(loader.getRoot()));
            popupStage.setResizable(false);
            popupStage.setOnCloseRequest(event -> opacityPane.setVisible(false));
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelDelete(ActionEvent e) {
        deleteCtn.setVisible(false);
        opacityPane.setVisible(false);
        showAlert.alert(Alert.AlertType.ERROR, "You have cancelled the action");
    }

    public void confirmDelete(ActionEvent e) {
        try {

            Trainee currentTrainee = TraineeSession.getInstance().getCurrentTrainee();
            if (currentTrainee != null) {
                traineeDao.delete(currentTrainee);

                showAlert.alert(Alert.AlertType.INFORMATION, "Your account has been successfully deleted!");

                deleteCtn.setVisible(false);
                opacityPane.setVisible(false);

                redirectToLoginPage();
            } else {
                showAlert.alert(Alert.AlertType.ERROR, "No account is selected to delete.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert.alert(Alert.AlertType.ERROR, "An error occurred while deleting your account. Please try again.");
        }
    }

    public void redirectToLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }


}
