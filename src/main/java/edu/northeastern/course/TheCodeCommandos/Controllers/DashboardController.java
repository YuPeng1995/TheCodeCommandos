package edu.northeastern.course.TheCodeCommandos.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.northeastern.course.TheCodeCommandos.Models.Board;
import edu.northeastern.course.TheCodeCommandos.Models.Card;
import edu.northeastern.course.TheCodeCommandos.Models.Model;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    public Text user_name;
    public Label login_date;
    public ListView<String> cards_listview;
    public FlowPane boards_container;
    public Button create_new_board_btn;

    // Everytime a window is loaded, the initialize() method is called
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        initBoards();
        initDoneCards();
        create_new_board_btn.setOnAction(e -> toNewBoard());
        cards_listview.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    Stage stage = (Stage)user_name.getScene().getWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                    Model.getInstance().getViewFactory().showTableWindow();
                }
            }
        });
    }

    // Change the welcome name and today info by property binding
    public void bindData() {
        user_name.textProperty().bind(Bindings.concat("Hi, ").concat(Model.getInstance().getMember().firstNameProperty()));
        login_date.setText("Today, " + LocalDate.now());
    }

    // Get boards from database and display them in the dashboard
    private void initBoards() {
        if (Model.getInstance().getBoards().isEmpty()) {
            Model.getInstance().setBoards(Model.getInstance().getBoards());
        }

        for (Board b: Model.getInstance().getBoards()) {
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(30, 30, 30, 30));
            vBox.setStyle("-fx-border-color: black");
            Label board_label = new Label(b.boardTitleProperty().getValue());
            Text description = new Text("\nDescription: " + b.descriptionProperty().getValue() + "\n");
            Text dueDate = new Text("Due date: " + b.dueDateProperty().getValue().toString());
            vBox.getChildren().addAll(board_label, description, dueDate);
            vBox.setOnMouseClicked(e -> toBoard(b.boardTitleProperty().getValue()));
            boards_container.getChildren().add(vBox);
        }
    }

    // Get all the Done cards from database and display them in the listview
    private void initDoneCards() {
        if (Model.getInstance().getAllDoneCards().isEmpty()) {
            Model.getInstance().setAllDoneCards();
        }
        for (Card c: Model.getInstance().getAllDoneCards()) {
            cards_listview.getItems().add(c.cardNameProperty().getValue());
        }
    }

    // Close the current window and turn to new board window
    private void toNewBoard() {
        Stage stage = (Stage)create_new_board_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showNewBoardWindow();
    }

    // Close the current window and turn to board window
    private void toBoard(String boardTitle) {
        Stage stage = (Stage)user_name.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showBoardWindow(boardTitle);
    }

}
