package edu.northeastern.course.TheCodeCommandos.Models;

import edu.northeastern.course.TheCodeCommandos.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.LocalDate;

public class Model {

	private static Model model;
	private final ViewFactory viewFactory;
	private final DatabaseDriver databaseDriver;

	private final Member member;
	private boolean memberLoginSuccessFlag;
	private final ObservableList<Board> boards;
	private final ObservableList<Card> allDoneCards;
	private final ObservableList<Member> allMembers;

	// Create everything
	private Model() {
		this.viewFactory = new ViewFactory();
		this.databaseDriver = new DatabaseDriver();

		this.memberLoginSuccessFlag = false;
		this.member = new Member("", "", "",  null);
		this.boards = FXCollections.observableArrayList();
		this.allDoneCards = FXCollections.observableArrayList();
		this.allMembers = FXCollections.observableArrayList();
	}

	// Static method: create a model instance
	public static synchronized Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	// Get view factory through model
	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	// Get database through model
	public DatabaseDriver getDatabaseDriver() {return databaseDriver;}

	// If user has successfully logged in, return true, otherwise return false
	public boolean getMemberLoginSuccessFlag() {
		return memberLoginSuccessFlag;
	}

	// Set whether the user has successfully logged in
	public void setMemberLoginSuccessFlag(boolean flag) {
		this.memberLoginSuccessFlag = flag;
	}

	// Get the member who logged in
	public Member getMember() {
		return member;
	}

	// Set allMembers data field through database ResultSet
	public void setAllMembers() {
		ResultSet resultSet = databaseDriver.getAllMember();
		try {
			while (resultSet.next()) {
				String firstName = resultSet.getString("FirstName");
				String lastName = resultSet.getString("LastName");
				String username = resultSet.getString("Username");
				String[] dateParts = resultSet.getString("Date").split("-");
				LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
				allMembers.add(new Member(firstName, lastName, username, date));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get allMembers data field
	public ObservableList<Member> getAllMembers() {
		return  allMembers;
	}

	// Check the username and password through database ResultSet
	public void evaluateClientCredentials(String username, String password) {
		ResultSet resultSet = databaseDriver.getMemberData(username, password);
		try {
			if (resultSet.isBeforeFirst()) {
				this.member.firstNameProperty().set(resultSet.getString("FirstName"));
				this.member.lastNameProperty().set(resultSet.getString("LastName"));
				this.member.usernameProperty().set(resultSet.getString("Username"));
				String[] dateParts = resultSet.getString("Date").split("-");
				LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
				this.member.dateProperty().set(date);
				this.memberLoginSuccessFlag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Set boards data field through database ResultSet
	public void setBoards(ObservableList<Board> boards) {
		ResultSet resultSet = databaseDriver.getBoardData();
		try {
			while (resultSet.next()){
				String boardTitle = resultSet.getString("BoardTitle");
				String description = resultSet.getString("Description");
				String[] dateParts = resultSet.getString("Date").split("-");
				LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
				boards.add(new Board(boardTitle, description, date));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	// Get boards data field
	public ObservableList<Board> getBoards() {
		return boards;
	}

	// Set cards with Done status through database ResultSet
	public void setAllDoneCards() {
		ResultSet resultSet = databaseDriver.getDoneCards();
		try {
			while (resultSet.next()){
				String cardName = resultSet.getString("CardName");
				String status = resultSet.getString("Status");
				String label = resultSet.getString("Label");
				String[] dateParts = resultSet.getString("Date").split("-");
				LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
				allDoneCards.add(new Card(cardName, status, label, date));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	// Get cards with Done status
	public ObservableList<Card> getAllDoneCards() {
		return allDoneCards;
	}
}
