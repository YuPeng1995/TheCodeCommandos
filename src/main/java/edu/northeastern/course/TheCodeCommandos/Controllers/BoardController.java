package edu.northeastern.course.TheCodeCommandos.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import edu.northeastern.course.TheCodeCommandos.Models.Board;
import edu.northeastern.course.TheCodeCommandos.Models.Card;
import edu.northeastern.course.TheCodeCommandos.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class BoardController implements Initializable {
	
    public Label board_title_lbl;
    public ListView<BorderPane> to_do_listview;
    public ListView<BorderPane> doing_listview;
    public ListView<Text> done_listview;
    public Text add_to_do_text;
    public Text add_doing_text;
    public Text add_done_text;
    public Board currentBoard;
    public Button delete_project_btn;
    public Label error_lbl;

    // Everytime a window is loaded, the initialize() method is called
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the chosen Board
    	currentBoard = Model.getInstance().getCurrentBoard();
        board_title_lbl.setText(currentBoard.boardTitleProperty().get());
        populateLists();
        
        add_to_do_text.setOnMouseClicked(e -> {
            Model.getInstance().getViewFactory().addCard().ifPresent(pair -> {
                String cardName = pair.getKey();
                LocalDate dueDate = pair.getValue();
                Model.getInstance().getDatabaseDriver().addCard(cardName, "To-do", dueDate, currentBoard.boardTitleProperty().get());
            });
            populateLists();
        });
        add_doing_text.setOnMouseClicked(e -> {
            Model.getInstance().getViewFactory().addCard();
            populateLists();
        });
        add_done_text.setOnMouseClicked(e -> {
            Model.getInstance().getViewFactory().addCard();
            populateLists();
        });

        delete_project_btn.setOnAction(e -> {
            Model.getInstance().getDatabaseDriver().deleteBoard(board_title_lbl.getText());
            board_title_lbl.setText("Project Name");
            to_do_listview.getItems().clear();
            doing_listview.getItems().clear();
            done_listview.getItems().clear();
            error_lbl.setText("Successfully deleted project");
        });

    }

    // To populate lists
    private void populateLists() {
        to_do_listview.getItems().clear();
        doing_listview.getItems().clear();
        done_listview.getItems().clear();

        List<Card> cards = Model.getInstance().getBoardCards(currentBoard.boardTitleProperty().get());
        for (Card card : cards) {
            BorderPane pane = new BorderPane();
            Text cardDisplay = new Text(card.cardNameProperty().get());
            Button button = new Button();
            button.setStyle("-fx-font-size:10");
            pane.setLeft(cardDisplay);
            pane.setRight(button);

            switch (card.statusProperty().get()) {
                case "To-do":
                    button.setText("Move to Doing");
                    button.setOnAction(e -> moveCardToNextStatus(card.cardNameProperty().get(), "Doing"));
                    to_do_listview.getItems().add(pane);
                    break;
                case "Doing":
                    button.setText("Move to Done");
                    button.setOnAction(e -> moveCardToNextStatus(card.cardNameProperty().get(), "Done"));
                    doing_listview.getItems().add(pane);
                    break;
                case "Done":
                    done_listview.getItems().add(cardDisplay);
                    break;
            }
        }
    }

    // Move the card to next status
    private void moveCardToNextStatus(String cardName, String nextStatus) {
        Model.getInstance().getDatabaseDriver().updateCardStatus(cardName, nextStatus);
        populateLists();
    }
}
