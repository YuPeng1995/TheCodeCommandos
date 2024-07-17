package edu.northeastern.course.TheCodeCommandos.Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Board {
    // Data field for Board
    private final StringProperty boardTitle;
    private final StringProperty description;
    private final ObservableList<Card> cards;
    private final ObjectProperty<LocalDate> dueDate;

    // Constructor
    public Board(String boardTitle, String description, LocalDate dueDate) {
        this.boardTitle = new SimpleStringProperty(this, "Default_title", boardTitle);
        this.description = new SimpleStringProperty(this, "Description", description);
        this.dueDate = new SimpleObjectProperty<>(this, "Date", dueDate);
        this.cards = FXCollections.observableArrayList();
    }

    // Four getter methods
    public StringProperty boardTitleProperty() {return boardTitle;}

    public StringProperty descriptionProperty() {return description;}

    public ObjectProperty<LocalDate> dueDateProperty() {return dueDate;}

    public ObservableList<Card> getCards() {return cards;}

}
