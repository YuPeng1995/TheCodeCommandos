package edu.northeastern.course.TheCodeCommandos.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Board implements Comparable<Board> {
    // Data field for Board
    private String boardTitle;
    private final String description;
    private final ObjectProperty<LocalDate> dueDate;

    // Constructor
    public Board(String boardTitle, String description, LocalDate dueDate) {
        this.boardTitle = boardTitle;
        this.description = description;
        this.dueDate = new SimpleObjectProperty<>(dueDate);
    }

    // Four getter methods
    public String getBoardTitle() {return boardTitle;}

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getDescription() {return description;}

    public ObjectProperty<LocalDate> dueDateProperty() {return dueDate;}

    public void add() {
        Model.getInstance().getDatabaseDriver().createNewBoard(boardTitle, description, dueDate.get());
    }

    public void delete() {
        Model.getInstance().getDatabaseDriver().deleteBoard(boardTitle);
    }

    @Override
    public int compareTo(Board b) {
        return this.dueDate.get().compareTo(b.dueDateProperty().get());
    }

}
