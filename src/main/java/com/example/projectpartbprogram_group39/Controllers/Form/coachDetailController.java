package com.example.projectpartbprogram_group39.Controllers.Form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class coachDetailController {

    @FXML
    private Label availableTime;

    @FXML
    private Label coachExpert;

    @FXML
    private ImageView coachImg;

    @FXML
    private Label coachesName;

    @FXML
    private Label day1;

    @FXML
    private Label day2;

    @FXML
    private Label day3;

    @FXML
    private Label time1;

    @FXML
    private Label time2;

    @FXML
    private Label time3;


    public void coachesDetail(Image coachesImg,String name, String expert, String available,
                              String firstDay, String secDay,String thirdDay,String clock1,String clock2,String clock3 ){
        coachImg.setImage(coachesImg);
        coachesName.setText(name);
        coachExpert.setText(expert);
        availableTime.setText(available);
        day1.setText(firstDay);
        day2.setText(secDay);
        day3.setText(thirdDay);
        time1.setText(clock1);
        time2.setText(clock2);
        time3.setText(clock3);
    }


}
