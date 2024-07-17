package edu.northeastern.course.TheCodeCommandos.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CalendarController {

    @FXML
    public GridPane dayContainer;

    @FXML
    public Label monthLabel;

    public YearMonth currentYearMonth;
    public Map<LocalDate, String> events;

    @FXML
    public void initialize() {
        currentYearMonth = YearMonth.now();
        events = new HashMap<>();
        drawCalendar(currentYearMonth);

        // 添加延迟加载样式表，以确保Scene已经存在
        dayContainer.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().add(getClass().getResource("/styles/Calendar.css").toExternalForm());
            }
        });
    }

    @FXML
    public void handlePreviousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        drawCalendar(currentYearMonth);
    }

    @FXML
    public void handleNextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        drawCalendar(currentYearMonth);
    }

    public void drawCalendar(YearMonth yearMonth) {
        dayContainer.getChildren().clear();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        monthLabel.setText(yearMonth.format(monthYearFormatter));

        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();
        DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

        // 添加星期几标签
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.getStyleClass().add("week-day-label");
            dayContainer.add(dayLabel, i, 0);
        }

        int column = firstDayOfWeek.getValue() % 7; // 将星期一至星期日转化为0至6
        int row = 1; // 从第1行开始，因为第0行显示星期几

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = firstDayOfMonth.plusDays(day - 1);
            VBox dayBox = createDayBox(date);

            dayContainer.add(dayBox, column, row);

            column++;
            if (column == 7) { // 每行显示7天
                column = 0;
                row++;
            }
        }
    }

    public VBox createDayBox(LocalDate date) {
        VBox dayBox = new VBox();
        dayBox.setSpacing(5);
        dayBox.getStyleClass().add("day-box");

        Label dayLabel = new Label(Integer.toString(date.getDayOfMonth()));
        dayLabel.setOnMouseClicked(e -> handleDateClick(date));

        if (LocalDate.now().equals(date)) {
            dayLabel.getStyleClass().add("current-day");
        }

        dayBox.getChildren().add(dayLabel);

        // Display event if exists
        if (events.containsKey(date)) {
            Label eventLabel = new Label(events.get(date));
            eventLabel.getStyleClass().add("event-label");
            eventLabel.setOnMouseClicked(e -> handleEventClick(date));
            dayBox.getChildren().add(eventLabel);
        }

        return dayBox;
    }

    public void handleDateClick(LocalDate date) {
        Stage dialog = new Stage();
        dialog.setTitle("Add Event on " + date.toString());

        VBox dialogVBox = new VBox();
        dialogVBox.setSpacing(10);

        Label eventLabel = new Label("Event:");
        TextField eventNameField = new TextField();

        Button addEventButton = new Button("Add Event");
        addEventButton.setOnAction(e -> {
            String event = eventNameField.getText();
            if (event != null && !event.isEmpty()) {
                events.put(date, event);
                drawCalendar(currentYearMonth);
            }
            dialog.close();
        });

        dialogVBox.getChildren().addAll(eventLabel, eventNameField, addEventButton);
        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void handleEventClick(LocalDate date) {
        Stage dialog = new Stage();
        dialog.setTitle("View/Edit Event on " + date.toString());

        VBox dialogVBox = new VBox();
        dialogVBox.setSpacing(10);

        Label eventLabel = new Label("Event:");
        TextField eventNameField = new TextField(events.get(date));

        Button updateEventButton = new Button("Update Event");
        updateEventButton.setOnAction(e -> {
            String event = eventNameField.getText();
            if (event != null && !event.isEmpty()) {
                events.put(date, event);
                drawCalendar(currentYearMonth);
            }
            dialog.close();
        });

        Button deleteEventButton = new Button("Delete Event");
        deleteEventButton.setOnAction(e -> {
            events.remove(date);
            drawCalendar(currentYearMonth);
            dialog.close();
        });

        dialogVBox.getChildren().addAll(eventLabel, eventNameField, updateEventButton, deleteEventButton);
        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}