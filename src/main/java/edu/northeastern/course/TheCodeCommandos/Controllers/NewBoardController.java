package edu.northeastern.course.TheCodeCommandos.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.northeastern.course.TheCodeCommandos.Models.Card;
import edu.northeastern.course.TheCodeCommandos.Models.DatabaseDriver;
import edu.northeastern.course.TheCodeCommandos.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class NewBoardController implements Initializable {

    public Button submit_btn;
    public Button new_card_btn;
    public TextField board_title_textfield;
    public TextArea description_textarea;
    public DatePicker date;
    public ListView<String> to_do_cards_listview;
    public DatabaseDriver databaseDriver;
    public ObservableList<Card> to_do_cards_list = FXCollections.observableArrayList();
    public Label error_lbl;

    // Everytime a window is loaded, the initialize() method is called
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseDriver = new DatabaseDriver();
        submit_btn.setOnAction(e -> submitNewBoard());
        new_card_btn.setOnAction(e -> Model.getInstance().getViewFactory().addCard().ifPresent(pair -> {
            String cardName = pair.getKey();
            LocalDate dueDate = pair.getValue();
            to_do_cards_listview.getItems().add(cardName);
            to_do_cards_list.add(new Card(cardName, "To-do", dueDate, board_title_textfield.getText()));
        }));
    }

    // Create a new board and update the database
    private void submitNewBoard() {
        String projectName = board_title_textfield.getText();
        String description = description_textarea.getText();
        LocalDate dueDate = date.getValue();

        if (projectName.isEmpty() || description.isEmpty() || dueDate == null) {
            // Handle empty fields or invalid input
            System.out.println("Please fill all fields.");
            return;
        }

        Model.getInstance().getDatabaseDriver().createNewBoard(projectName, description, dueDate);
        for (Card c : to_do_cards_list) {
            Model.getInstance().getDatabaseDriver().addCard(
                    c.cardNameProperty().get(),
                    c.statusProperty().get(),
                    c.dueDateProperty().get(),
                    c.boardProperty().get()
            );
        }
        emptyFields();
        error_lbl.setText("Successfully submitted!");

    }

    // Empty all text fields
    private void emptyFields() {
        board_title_textfield.setText("");
        description_textarea.setText("");
        date.setValue(null);
        to_do_cards_listview.getItems().clear();
    }

}
