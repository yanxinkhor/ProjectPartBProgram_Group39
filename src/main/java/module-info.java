module com.example.projectpartbprogram_group39 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projectpartbprogram_group39 to javafx.fxml;
    exports com.example.projectpartbprogram_group39;
    exports com.example.projectpartbprogram_group39.Controllers.Form;
    opens com.example.projectpartbprogram_group39.Controllers.Form to javafx.fxml;
    exports com.example.projectpartbprogram_group39.Models;
    opens com.example.projectpartbprogram_group39.Models to javafx.fxml;
    exports com.example.projectpartbprogram_group39.Controllers.Service;
    opens com.example.projectpartbprogram_group39.Controllers.Service to javafx.fxml;
}