package edu.northeastern.course.TheCodeCommandos.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
	
	private AnchorPane dashboardView;
	private AnchorPane membersView;
	
	public ViewFactory() {};
	
//	public AnchorPane getDashboardView() {
//		if (dashboardView != null) {
//			try {
//				dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return dashboardView;
//	}
//	
//	public AnchorPane getMembersView() {
//		if (membersView != null) {
//			try {
//				membersView = new FXMLLoader(getClass().getResource("/Fxml/Client/Members.fxml")).load();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return membersView;
//	}
	
	public void showLoginWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
		createStage(loader);
	}
	
	public void showDashboardWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml"));
		createStage(loader);
	}
	
	public void showMembersWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Members.fxml"));
		createStage(loader);
	}
	
	public void showTableWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Table.fxml"));
		createStage(loader);
	}
	
	public void showCalendarWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Calendar.fxml"));
		createStage(loader);
	}
	
	public void showBoard_1Window() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Board.fxml"));
		createStage(loader);
	}
	
	public void showNewBoardWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/NewBoard.fxml"));
		createStage(loader);
	}
	
	public void createStage(FXMLLoader loader) {
		Scene scene = null;
		try {
			scene = new Scene(loader.load());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Trello");
		stage.show();
	}
	
	public void closeStage(Stage stage) {
		stage.close();
	}

}
