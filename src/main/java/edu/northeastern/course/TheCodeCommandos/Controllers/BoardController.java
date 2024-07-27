package edu.northeastern.course.TheCodeCommandos.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.northeastern.course.TheCodeCommandos.Models.Card;
import edu.northeastern.course.TheCodeCommandos.Models.Model;
import javafx.collections.ObservableList;
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
    public Button delete_project_btn;
    public Label error_lbl;
    public Label card_error_lbl;
    public Label due_date;

    // Everytime a window is loaded, the initialize() method is called
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the chosen Board
        String boardTitle = Model.getInstance().getCurrentBoard().getBoardTitle();
        board_title_lbl.setText(boardTitle);
        due_date.setText("Due :" + Model.getInstance().getCurrentBoard().dueDateProperty().get().toString());
        populateLists();

        add_to_do_text.setOnMouseClicked(e -> {
            Model.getInstance().getViewFactory().addCard().ifPresent(pair -> {
                String cardName = pair.getKey();
                LocalDate dueDate = pair.getValue();
                if (Model.getInstance().getCardNameHashSet().contains(cardName)) {
                    card_error_lbl.setText("Card already exists");
                } else {
                    card_error_lbl.setText("");
                    new Card(cardName, "To-do", dueDate, boardTitle).add();
                }
            });
            populateLists();
        });
        add_doing_text.setOnMouseClicked(e -> {
            Model.getInstance().getViewFactory().addCard().ifPresent(pair -> {
                String cardName = pair.getKey();
                LocalDate dueDate = pair.getValue();
                if (Model.getInstance().getCardNameHashSet().contains(cardName)) {
                    card_error_lbl.setText("Card already exists");
                } else {
                    card_error_lbl.setText("");
                    new Card(cardName, "Doing", dueDate, boardTitle).add();
                }
            });
            populateLists();
        });
        add_done_text.setOnMouseClicked(e -> {
            Model.getInstance().getViewFactory().addCard().ifPresent(pair -> {
                String cardName = pair.getKey();
                LocalDate dueDate = pair.getValue();
                if (Model.getInstance().getCardNameHashSet().contains(cardName)) {
                    card_error_lbl.setText("Card already exists");
                } else {
                    card_error_lbl.setText("");
                    new Card(cardName, "Done", dueDate, boardTitle).add();
                }
            });
            populateLists();
        });

        delete_project_btn.setOnAction(e -> {
            Model.getInstance().getCurrentBoard().delete();
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

        ObservableList<Card> cards = Model.getInstance().getBoardCards();
        cards.clear();
        Model.getInstance().setCardsForBoard(board_title_lbl.getText());
        for (Card card : cards) {
            BorderPane pane = new BorderPane();
            Text cardDisplay = new Text(card.getCardName());
            Button button = new Button();
            button.setStyle("-fx-font-size:10");
            pane.setLeft(cardDisplay);
            pane.setRight(button);

            switch (card.getStatus()) {
                case "To-do":
                    button.setText("Move to Doing");
                    button.setOnAction(e -> moveCardToNextStatus(card.getCardName(), "Doing"));
                    to_do_listview.getItems().add(pane);
                    break;
                case "Doing":
                    button.setText("Move to Done");
                    button.setOnAction(e -> moveCardToNextStatus(card.getCardName(), "Done"));
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
