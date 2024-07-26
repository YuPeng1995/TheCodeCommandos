package edu.northeastern.course.TheCodeCommandos.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Card {
    // Data field for Board
    private final StringProperty cardName;
    private final StringProperty status;
    private final ObjectProperty<LocalDate> dueDate;
    private final StringProperty board;

    // Constructor
    public Card(String cardName, String status, LocalDate dueDate, String board) {
        this.cardName = new SimpleStringProperty(this, "Default_card_name", cardName);
        this.status = new SimpleStringProperty(this, "To-do", status);
        this.dueDate = new SimpleObjectProperty<>(this, "Date", dueDate);
        this.board = new SimpleStringProperty(this, "Default_board", board);
    }

    // Four getter methods
    public StringProperty cardNameProperty() {return cardName;}

    public StringProperty statusProperty() {return status;}

    public ObjectProperty<LocalDate> dueDateProperty() {return dueDate;}

    public StringProperty boardProperty() {return board;}

}