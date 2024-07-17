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
    private final StringProperty label;
    private final ObjectProperty<LocalDate> dueDate;

    // Constructor
    public Card(String cardName, String status, String label, LocalDate dueDate) {
        this.cardName = new SimpleStringProperty(this, "Default_card_name", cardName);
        this.status = new SimpleStringProperty(this, "To-do", status);
        this.label = new SimpleStringProperty(this, "Default_label", label);
        this.dueDate = new SimpleObjectProperty<>(this, "Date", dueDate);
    }

    // Four getter methods
    public StringProperty cardNameProperty() {return cardName;}

    public StringProperty statusProperty() {return status;}

    public StringProperty labelProperty() {return label;}

    public ObjectProperty<LocalDate> dueDateProperty() {return dueDate;}

}