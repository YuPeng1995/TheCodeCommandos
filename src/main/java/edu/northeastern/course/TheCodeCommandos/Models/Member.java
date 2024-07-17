package edu.northeastern.course.TheCodeCommandos.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Member {
    // Data field for Member
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty username;
    private final ObjectProperty<LocalDate> dateCreated;

    // Constructor
    public Member(String fName, String lName, String username, LocalDate date) {
        this.firstName = new SimpleStringProperty(this, "FirstName", fName);
        this.lastName = new SimpleStringProperty(this, "LastName", lName);
        this.username = new SimpleStringProperty(this, "Username", username);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date", date);
    }

    // Four getter methods
    public StringProperty firstNameProperty() {return firstName;}

    public StringProperty lastNameProperty() {return lastName;}

    public StringProperty usernameProperty() {return username;}

    public ObjectProperty<LocalDate> dateProperty() {return dateCreated;}
}