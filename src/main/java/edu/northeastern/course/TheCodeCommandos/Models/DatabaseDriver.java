package edu.northeastern.course.TheCodeCommandos.Models;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public ResultSet getAllMemberData() {
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
    public ResultSet getBoardsData() {
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

    // Create a new board in the database
    public void createNewBoard(String projectName, String description, LocalDate date) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            String dateString = date.toString();
            statement.executeUpdate("INSERT INTO Boards (BoardTitle, Description, Date) VALUES ('"
                    + projectName + "', '" + description + "', '" + dateString + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                this.conn.rollback();  // Rollback the transaction in case of an error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
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


    // Get cards for the board
    public List<Card> getCardsForBoard(String boardTitle) {
        List<Card> cards = new ArrayList<>();
        try {
            String query = "SELECT * FROM Cards WHERE Board = '" + boardTitle + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String cardName = resultSet.getString("CardName");
                String status = resultSet.getString("Status");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                cards.add(new Card(cardName, status, date, boardTitle));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    // Add cards in the database
    public void addCard(String cardName, String status, LocalDate date, String boardTitle) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO Cards (CardName, Status, Date, Board) VALUES ('"
                    + cardName + "', '" + status + "', '" + date + "', '" + boardTitle + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update cards in the database
    public void updateCardStatus(String cardName, String newStatus) {
        String query = "UPDATE Cards SET Status = ? WHERE CardName = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, newStatus);
            statement.setString(2, cardName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBoard(String boardName) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM Boards WHERE BoardTitle='"+boardName+"'");
            statement.executeUpdate("DELETE FROM Cards WHERE Board='"+boardName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCard(String cardName, String boardName) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM Cards WHERE CardName='"+cardName+"' AND Board='"+boardName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
