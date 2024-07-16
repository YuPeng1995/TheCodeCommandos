package edu.northeastern.course.TheCodeCommandos.Controllers.Client;

import java.net.URL;
import java.util.ResourceBundle;

import edu.northeastern.course.TheCodeCommandos.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class SideBarController implements Initializable {
	
	public Button dashboard_btn;
    public Button members_btn;
    public Button table_btn;
    public Button calendar_btn;
    public Button board_1_btn;
    public Button new_board_btn;
    public Button logout_btn;
    public Button report_btn;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	dashboard_btn.setOnAction(e -> toDashboard());
    	members_btn.setOnAction(e -> toMembers());
    	table_btn.setOnAction(e -> toTable());
    	calendar_btn.setOnAction(e -> toCalendar());
    	board_1_btn.setOnAction(e -> toBoard_1());
    	new_board_btn.setOnAction(e -> toNewBoard());
    }
    
    public void toDashboard() {
    	closeStage();
    	Model.getInstance().getViewFactory().showDashboardWindow();
    }
    
    public void toMembers() {
    	closeStage();
    	Model.getInstance().getViewFactory().showMembersWindow();
    }
    
    public void toTable() {
    	closeStage();
    	Model.getInstance().getViewFactory().showTableWindow();
    }
    
    public void toCalendar() {
    	closeStage();
    	Model.getInstance().getViewFactory().showCalendarWindow();
    }
    
    public void toBoard_1() {
    	closeStage();
    	Model.getInstance().getViewFactory().showBoard_1Window();
    }
    
    public void toNewBoard() {
    	closeStage();
    	Model.getInstance().getViewFactory().showNewBoardWindow();
    }
    
    public void closeStage() {
    	Stage stage = (Stage)dashboard_btn.getScene().getWindow();
    	Model.getInstance().getViewFactory().closeStage(stage);
    }
}
