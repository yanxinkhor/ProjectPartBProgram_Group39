module com.example.projectpartbprogram_group39 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectpartbprogram_group39 to javafx.fxml;
    exports com.example.projectpartbprogram_group39;
    exports com.example.projectpartbprogram_group39.Controllers;
    opens com.example.projectpartbprogram_group39.Controllers to javafx.fxml;
}