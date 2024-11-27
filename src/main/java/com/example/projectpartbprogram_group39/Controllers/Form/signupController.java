package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.TraineeDaoImp;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.Encryption;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;



public class signupController {
    @FXML
    private TextField txtUsername, txtAge, txtPhoneNo, txtEmail, txtHeight, txtWeight;


    @FXML
    private RadioButton femaleRadioBtn, maleRadioBtn;

    @FXML
    private ToggleGroup Gender;

    @FXML
    private PasswordField txtPassword, txtPasswordConfirmed;

    @FXML
    private Button passwordBtn, passwordConfirmedBtn;

    @FXML
    private ImageView visiblePassword, visibleConfirmedPass;

    @FXML
    private TextField txtPasswordVisible, txtVisiblePasswordConfirmed;


    private final Image passwordVisible = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/password_visible.png"));
    private final Image passwordInvisible = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/password_invisible.png"));
    private final TraineeDaoImp traineeDao = new TraineeDaoImp();


    public void signup(ActionEvent e) throws IOException{
        String username = txtUsername.getText();
        String ageString = txtAge.getText();
        String phoneNoString = txtPhoneNo.getText();
        String email = txtEmail.getText();
        String gender = femaleRadioBtn.isSelected() ? "Female" : maleRadioBtn.isSelected() ? "Male" : "";
        String heightString = txtHeight.getText();
        String weightString = txtWeight.getText();
        String password = txtPassword.getText();
        String passwordConfirmed = txtPasswordConfirmed.getText();


        if (username.isEmpty() || ageString.isEmpty() || phoneNoString.isEmpty() || email.isEmpty() || gender.isEmpty() ||
                heightString.isEmpty() || weightString.isEmpty() || password.isEmpty() || passwordConfirmed.isEmpty()) {
            showAlert.alert(Alert.AlertType.ERROR, "Please fill in all the credential");
            return;
        }

        if (!password.equals(passwordConfirmed)) {
            showAlert.alert(Alert.AlertType.ERROR, "The password do not match!");
            txtPasswordConfirmed.setText("");
            return;
        }

        try {
            int age = Integer.parseInt(ageString);
            int phoneNo = Integer.parseInt(phoneNoString);
            double height = Double.parseDouble(heightString);
            double weight = Double.parseDouble(weightString);

            String encryptedPassword = Encryption.hashPassword(password);

            if (traineeDao.userExists(username,email)) {
                showAlert.alert(Alert.AlertType.ERROR, "User already exists!");
                txtUsername.setText("");
                txtAge.setText("");
                txtPhoneNo.setText("");
                txtEmail.setText("");
                Gender.selectToggle(null);
                txtHeight.setText("");
                txtWeight.setText("");
                txtPassword.setText("");
                txtPasswordConfirmed.setText("");

            }else {

                Trainee trainee = new Trainee(username, age, gender, phoneNo, email, height, weight, encryptedPassword);
                traineeDao.StoreTrainee(trainee);
                showAlert.alert(Alert.AlertType.INFORMATION, "Signup successful!");
                back(e);
            }

        } catch (NumberFormatException event) {
            showAlert.alert(Alert.AlertType.ERROR, "please enter a valid credential");

        }

    }
    boolean isVisible = false;

    public void displayPassword(ActionEvent e){

        Button sourceBtn = (Button)e.getSource();

        if(sourceBtn == passwordBtn){
            changeVisibilityIcon(txtPassword, txtPasswordVisible, visiblePassword);
        }else if(sourceBtn == passwordConfirmedBtn){
            changeVisibilityIcon(txtPasswordConfirmed, txtVisiblePasswordConfirmed, visibleConfirmedPass);
        }

    }

    public void changeVisibilityIcon(PasswordField passwordField, TextField textField, ImageView imgView){
        if(isVisible){
            passwordField.setText(textField.getText());
            textField.setVisible(false);
            passwordField.setVisible(true);

            imgView.setImage(passwordVisible);
            isVisible = false;

        }else{
            textField.setText(passwordField.getText());
            passwordField.setVisible(false);
            textField.setVisible(true);

            imgView.setImage(passwordInvisible);
            isVisible = true;
        }
    }

    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/login-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();

    }

}
