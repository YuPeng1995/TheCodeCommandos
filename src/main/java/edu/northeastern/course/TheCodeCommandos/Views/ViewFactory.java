package edu.northeastern.course.TheCodeCommandos.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Create a view factory which contains methods to show different page
 */
public class ViewFactory {
	
	public ViewFactory() {}
	
	// Show login window
	public void showLoginWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
		createStage(loader);
	}

	// Show dashboard window
	public void showDashboardWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml"));
		createStage(loader);
	}

	// Show members window
	public void showMembersWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Members.fxml"));
		createStage(loader);
	}

	// Show table window
	public void showTableWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Table.fxml"));
		createStage(loader);
	}

	// Show calendar window
	public void showCalendarWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Calendar.fxml"));
		createStage(loader);
	}

	// Show board window
	public void showBoardWindow(String boardTitle) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Board.fxml"));
		createStage(loader);
	}

	// Show new board window
	public void showNewBoardWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/NewBoard.fxml"));
		createStage(loader);
	}
	
	// Create stage based on loader
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
	
	// Close stage
	public void closeStage(Stage stage) {
		stage.close();
	}

}
