package edu.northeastern.course.TheCodeCommandos.Models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {

    // Database connector
    private Connection conn;

    // Create database driver and connect to the Trello database
    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:trello.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get login member data from database
    public ResultSet getMemberData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Members WHERE Username='"+username+"' AND Password='"+password+"';");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    // Get all member data from database
    public ResultSet getAllMember() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Members");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    // Get all board data from database
    public ResultSet getBoardData() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Boards");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    // Get cards from one board from database
    public ResultSet getCards(Board board) {
        Statement statement;
        ResultSet resultSet = null;
        String boardTitle = board.boardTitleProperty().getValue();
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Cards WHERE Board='"+boardTitle+"'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    // Get cards with Done status from database
    public ResultSet getDoneCards() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Cards WHERE Status='Done'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    // Create a new member in the database
    public void createNewMember(String fName, String lName, String username, String password, LocalDate date) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Members (FirstName, LastName, Username, Password, Date)" +
                    "VALUES ('"+fName+"', '"+lName+"', '"+username+"', '"+password+"', '"+date.toString()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a member from the database
    public void deleteMember(String username) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM Members WHERE Username='"+username+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
