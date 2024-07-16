package edu.northeastern.course.TheCodeCommandos.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import edu.northeastern.course.TheCodeCommandos.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
    public TextField username_field;
    public TextField password_field;
    public Button login_btn;
    public Label username_label;
    public Label password_label;
    public Label error_label;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBoundle) {
    	login_btn.setOnAction(e -> onLogin());
    }
    
    public void onLogin() {
    	Stage stage = (Stage)error_label.getScene().getWindow();
    	Model.getInstance().getViewFactory().closeStage(stage);
    	Model.getInstance().getViewFactory().showDashboardWindow();
    }
}
