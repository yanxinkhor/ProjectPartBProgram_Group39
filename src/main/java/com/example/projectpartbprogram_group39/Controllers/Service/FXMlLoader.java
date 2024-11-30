package com.example.projectpartbprogram_group39.Controllers.Service;

import com.example.projectpartbprogram_group39.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FXMlLoader {

    private Pane view;

    public Pane getView(String fileName) {

        try {
            URL fileUrl = MainApp.class.getResource("/com/example/projectpartbprogram_group39/View/" + fileName + ".fxml");

            if (fileUrl == null) {
                throw new IOException("FXML file not found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            view = loader.load();

        } catch (IOException e) {

            System.out.println("Error loading FXML file: " + fileName);
            e.printStackTrace();
        }
        return view;
    }
}


