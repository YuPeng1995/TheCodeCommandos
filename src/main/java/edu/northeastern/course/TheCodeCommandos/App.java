package edu.northeastern.course.TheCodeCommandos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
		Scene scene = new Scene(fxmlloader.load());
		stage.setScene(scene);
		stage.show();
	}
}