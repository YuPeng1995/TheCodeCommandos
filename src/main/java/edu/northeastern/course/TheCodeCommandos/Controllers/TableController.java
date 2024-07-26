package edu.northeastern.course.TheCodeCommandos.Controllers;

import edu.northeastern.course.TheCodeCommandos.Models.Board;
import edu.northeastern.course.TheCodeCommandos.Models.Card;
import edu.northeastern.course.TheCodeCommandos.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    public ChoiceBox<String> project_choicebox;
    public GridPane cards_gridpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGridPaneTitle();
        setupChoiceBox();
    }

    private void setupChoiceBox() {
        Model.getInstance().getBoards().clear();
        Model.getInstance().setBoards(Model.getInstance().getBoards());
        for (Board b: Model.getInstance().getBoards()) {
            Label project = new Label(b.boardTitleProperty().getValue());
            project_choicebox.getItems().add(project.getText());
        }
        project_choicebox.valueProperty().addListener((observable, oldValue, newValue) -> setupGridPane(newValue));
    }

    private void setupGridPaneTitle() {
        cards_gridpane.add(new Label("Card"), 0, 0);
        cards_gridpane.add(new Label("Status"), 1, 0);
        cards_gridpane.add(new Label("Due date"), 2, 0);
        cards_gridpane.add(new Label("Action"), 3, 0);
    }

    private void setupGridPane(String boardTitle) {
        cards_gridpane.getChildren().clear();
        setupGridPaneTitle();
        Model.getInstance().getBoardCards(boardTitle).clear();
        Model.getInstance().setCardsForBoard(Model.getInstance().getBoardCards(boardTitle));
        for (int i = 1; i <= Model.getInstance().getBoardCards(boardTitle).size(); i++) {
            Card c = Model.getInstance().getBoardCards(boardTitle).get(i - 1);
            cards_gridpane.add(new Label(c.cardNameProperty().getValue()), 0, i);
            cards_gridpane.add(new Label(c.statusProperty().getValue()), 1, i);
            cards_gridpane.add(new Label(c.dueDateProperty().getValue().toString()), 2, i);
            Button delete = new Button("Delete");
            delete.setOnAction(e -> {
                Model.getInstance().getDatabaseDriver().deleteCard(c.cardNameProperty().get(), boardTitle);
                setupGridPane(boardTitle);
            });
            cards_gridpane.add(delete, 3, i);
        }
    }
}
