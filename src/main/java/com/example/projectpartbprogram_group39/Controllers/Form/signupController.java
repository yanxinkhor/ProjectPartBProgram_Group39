package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.DAO.TraineeDaoImp;
import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class signupController {
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private TextField txtEmail;

    @FXML
    private RadioButton femaleRadioBtn, maleRadioBtn;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtWeight;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPasswordConfirmed;

    @FXML
    private Button createBtn, backBtn;

    private final TraineeDaoImp traineeDao = new TraineeDaoImp();


    public void signup(ActionEvent e) {
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

            if (traineeDao.userExists(username,email)) {
                showAlert.alert(Alert.AlertType.ERROR, "User already exists!");
            }else {

                Trainee trainee = new Trainee(username, age, gender, phoneNo, email, height, weight, password);
                traineeDao.StoreTrainee(trainee);
                showAlert.alert(Alert.AlertType.INFORMATION, "Signup successful!");
            }


        } catch (NumberFormatException event) {
            showAlert.alert(Alert.AlertType.ERROR, "please enter a valid credential");

        } catch(IOException ex){
            throw new RuntimeException();
        }

    }

  /*  private boolean userExist(String username, String email) throws IOException {
        File file = new File("userInfo.txt");

        if (!file.exists()) {
            System.out.println("userInfo.txt file does not exist.");
            return false;
        }

        List<String> lines = Files.readAllLines(Paths.get("userInfo.txt"));
        for (String line : lines) {
            try {
                Trainee trainee = Trainee.splitString(line);
                if (trainee.getUsername().equals(username) && trainee.getEmail().equals(email)) {
                    return true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping malformed line: " + line);
            }
        }
        return false;
    }

   */

  /*  private void storeTraineeInfo(Trainee trainee) throws IOException {
        File file = new File("userInfo.txt");


        if (!file.exists()) {
            System.out.println("File does not exist. Creating a new file...");
        } else {
            System.out.println("File already exists.");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(trainee.toString());
            writer.newLine();
            writer.flush();

        } catch (IOException ex) {
            System.err.println("Error writing to file: " + ex.getMessage());
            throw ex;
        }
    }

   */

    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectpartbprogram_group39/View/login-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();

    }


}
