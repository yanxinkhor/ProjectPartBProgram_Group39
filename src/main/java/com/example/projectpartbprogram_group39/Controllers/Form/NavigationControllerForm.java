package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Controllers.Service.NavigationController;
import com.example.projectpartbprogram_group39.Models.Devices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class NavigationControllerForm implements Initializable {

    @FXML
    private ImageView profile_img;

    @FXML
    private Label welcomeText;

    @FXML
    private Text displayUsername;

    @FXML
    private Text displayEmail;

    @FXML
    private Pane contentPane;

    @FXML
    private ImageView deviceImg1,deviceImg2,deviceImg3;

    @FXML
    private Label deviceLbl,connectedState;

    private final NavigationController navigationService = new NavigationController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigationService.initializeUI(contentPane, welcomeText, profile_img, displayUsername, displayEmail);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/dashboard-view.fxml"));
        Parent dashboardView;
        try {
            dashboardView = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(dashboardView);
        try {
            displayDeviceUI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void SwitchAction(ActionEvent e) throws IOException {
        String sourceBtn = ((Button) e.getSource()).getText();
        navigationService.handleSwitchAction(sourceBtn, contentPane, welcomeText);
    }

    public void logOut(ActionEvent e) throws IOException {
        navigationService.logOut(e);
    }

    public void displayDeviceUI() throws IOException {
        List<Devices> list = navigationService.getAllDeviceList();
        boolean anyDeviceConnected = false;
        for(Devices device:list){
            if (device.getDevicesName().equals("Apple Watch Series 9")) {
                deviceImg1.setVisible(device.getIsConnected());
                if (device.getIsConnected()) {
                    deviceLbl.setText("Apple Watch Series 9");
                    connectedState.setText("connected");
                    anyDeviceConnected = true;
                }else {
                    connectedState.setText("disconnected");
                }
            }else if(device.getDevicesName().equals("Apple Watch Ultra 2")){
                deviceImg2.setVisible(device.getIsConnected());
                if(device.getIsConnected()){
                    deviceLbl.setText("Apple Watch Ultra 2");
                    connectedState.setText("connected");
                    anyDeviceConnected = true;
                }else {
                    connectedState.setText("disconnected");
                }
            }else if(device.getDevicesName().equals("Apple Watch Series 10")){
                deviceImg3.setVisible(device.getIsConnected());
                if(device.getIsConnected()){
                    deviceLbl.setText("Apple Watch Series 10");
                    connectedState.setText("connected");
                    anyDeviceConnected = true;
                }else {
                    connectedState.setText("disconnected");
                }
            }

        }
        if (!anyDeviceConnected) {
            deviceLbl.setText("No Device Connected");
            connectedState.setText("disconnected");
        }
    }


}