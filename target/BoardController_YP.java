package edu.northeastern.course.TheCodeCommandos.Controllers;

import edu.northeastern.course.TheCodeCommandos.Models.Card;
import edu.northeastern.course.TheCodeCommandos.Models.Model;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    public Label board_title_lbl;
    public ListView<String> to_do_listview;
    public Text add_to_do_text;
    public ListView<String> doing_listview;
    public Text add_doing_text;
    public ListView<String> done_listview;
    public Text add_done_text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board_title_lbl.textProperty().bind(Model.getInstance().getSelectedBoard().boardTitleProperty());
        initToDoList();
        initDoingList();
        initDoneList();
        to_do_listview.setOnDragEntered(e -> {
            System.out.println("drag detected");
            System.out.println(e.getDragboard());
//            to_do_listview.getItems().remove(e.getPickResult().toString());
        });
        to_do_listview.setOnDragExited(e -> {
            System.out.println("drag done");
        });
    }

    private void initToDoList() {
        for (Card card : Model.getInstance().getSelectedToDoCards()) {
            to_do_listview.getItems().add(card.cardNameProperty().getValue());
        }
    }

    private void initDoingList() {
        for (Card card : Model.getInstance().getSelectedDoingCards()) {
            doing_listview.getItems().add(card.cardNameProperty().getValue());
        }
    }

    private void initDoneList() {
        for (Card card : Model.getInstance().getSelectedDoneCards()) {
            done_listview.getItems().add(card.cardNameProperty().getValue());
        }
    }

}
