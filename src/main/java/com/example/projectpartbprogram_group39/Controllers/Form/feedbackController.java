package com.example.projectpartbprogram_group39.Controllers.Form;

import com.example.projectpartbprogram_group39.Models.Trainee;
import com.example.projectpartbprogram_group39.Utils.TraineeSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class feedbackController implements Initializable {

    @FXML
    private Pane opacityPane;
    @FXML
    private TextArea feedbackText;
    @FXML
    private TextArea reportTextArea;
    @FXML
    private ComboBox<String> comb;
    @FXML
    private ImageView rateBtn1;
    @FXML
    private ImageView rateBtn2;
    @FXML
    private ImageView rateBtn3;
    @FXML
    private ImageView rateBtn4;
    @FXML
    private ImageView rateBtn5;
    @FXML
    private Button SF;
    @FXML
    private Button SR;
    @FXML
    private TextArea feedbackTA;
    @FXML
    private TextArea reportTA;

    private String selectedIssue = "";

    public String[] issues = {"Chat", "Statistics", "Tasks & Reminders", "My Activities", "Coaches"};

    private final javafx.scene.image.Image filledStar = new javafx.scene.image.Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/lightStar.png"));
    private final javafx.scene.image.Image emptyStar = new Image(getClass().getResourceAsStream("/com/example/projectpartbprogram_group39/Images/noStar.png"));


    @FXML
    public void setOpacityPane(Pane opacityPane) {
        this.opacityPane = opacityPane;
    }

    Trainee trainee = TraineeSession.getInstance().getCurrentTrainee();

    public void setTrainee(Trainee trainee) {
        if (trainee.getUsername().equals("Admin") && trainee.getPassword().equals("admin1234")) {
            if (SF != null) {
                SF.setVisible(true);
            }
            if (SR != null) {
                SR.setVisible(true);
            }
            loadFeedbacksAndReports();
        } else {
            if (SF != null) {
                SF.setVisible(false);
            }
            if (SR != null) {
                SR.setVisible(false);
            }
        }
    }

    public void seeFeedbacks(ActionEvent actionEvent) {
        openPopup("/com/example/projectpartbprogram_group39/View/getFeedbacks-view.fxml", "See Feedbacks");
    }

    public void seeReports(ActionEvent actionEvent) {
        openPopup("/com/example/projectpartbprogram_group39/View/getReports-view.fxml", "See Reports");
    }

    public void loadFeedbacksAndReports() {
        try {
            File feedbackFile = new File("feedback.txt");
            File reportFile = new File("report.txt");

            if (!feedbackFile.exists() || !reportFile.exists()) {
                System.out.println("file doesn't exist");
            }

            StringBuilder feedbackContent = new StringBuilder();
            StringBuilder reportContent = new StringBuilder();

            try (BufferedReader feedbackReader = new BufferedReader(new FileReader(feedbackFile));
                 BufferedReader reportReader = new BufferedReader(new FileReader(reportFile))) {

                String line;

                int feedbackCounter = 1;
                while ((line = feedbackReader.readLine()) != null) {
                    feedbackContent.append(feedbackCounter).append(". ").append(line).append(System.lineSeparator());
                    feedbackCounter++;
                }

                int reportCounter = 1;
                while ((line = reportReader.readLine()) != null) {
                    reportContent.append(reportCounter).append(". ").append(line).append(System.lineSeparator());
                    reportCounter++;
                }
            }

            if (feedbackTA != null) {
                feedbackTA.setText(feedbackContent.toString());
            }

            if (reportTA != null) {
                reportTA.setText(reportContent.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToFeedback(ActionEvent event) {
        openPopup("/com/example/projectpartbprogram_group39/View/generalFeedback-view.fxml", "General Feedback");
    }

    public void goToReport(ActionEvent event) {
        openPopup("/com/example/projectpartbprogram_group39/View/reportIssue-view.fxml", "Report Issue");
    }

    public void thankyouMsg(ActionEvent event) {
        if (!feedbackText.getText().trim().isEmpty()) {
            try (BufferedWriter output = new BufferedWriter(new FileWriter("feedback.txt", true))) {
                String feedback = "User: " + trainee.getUsername()+ " || " + feedbackText.getText();
                output.write(feedback + System.lineSeparator());
                loadFeedbacksAndReports();

                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Thank You for Your Feedback!");
                alert.setTitle("General Feedback");
                alert.showAndWait();
                feedbackText.clear();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Feedback cannot be empty");
            alert.showAndWait();
        }
    }

    public void reportMsg(ActionEvent event) throws IOException {
        if (!reportTextArea.getText().trim().isEmpty()) {
            try (BufferedWriter output = new BufferedWriter(new FileWriter("report.txt", true))) {
                String report = "User: " + trainee.getUsername()+ " || "+ selectedIssue + ", " + reportTextArea.getText();
                output.write(report + System.lineSeparator());
                loadFeedbacksAndReports();

                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Thank You for Your Report!");
                alert.setTitle("Report");
                alert.showAndWait();
                reportTextArea.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Report cannot be empty", ButtonType.OK);
            alert.showAndWait();
        }
    }

    boolean isClicked = false;
    public void rate1(ActionEvent event) {
        if(isClicked){
            rateBtn1.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn1.setImage(emptyStar);
            isClicked = true;
        }
    }
    public void rate2(ActionEvent event) {
        if(isClicked){
            rateBtn2.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn2.setImage(emptyStar);
            isClicked = true;
        }
    }
    public void rate3(ActionEvent event) {
        if(isClicked){
            rateBtn3.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn3.setImage(emptyStar);
            isClicked = true;
        }
    }
    public void rate4(ActionEvent event) {
        if(isClicked){
            rateBtn4.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn4.setImage(emptyStar);
            isClicked = true;
        }
    }
    public void rate5(ActionEvent event) {
        if(isClicked){
            rateBtn5.setImage(filledStar);
            isClicked = false;
        }else {
            rateBtn5.setImage(emptyStar);
            isClicked = true;
        }
    }

    private void openPopup(String fxmlFileName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            loader.load();

            feedbackController controller = loader.getController();
            controller.setTrainee(trainee);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle(title);

            popupStage.setScene(new Scene(loader.getRoot()));
            popupStage.setResizable(false);

            popupStage.setOnCloseRequest(event -> {
                popupStage.close();
                if (opacityPane != null) {
                    opacityPane.setVisible(false);
                }
            });
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTrainee(trainee);
        if (comb != null) {
            comb.getItems().addAll(issues);
            comb.setOnAction(event -> selectedIssue = comb.getValue());
        }

    }
}
