package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class AddItemFormController {

    private DatabaseHandler databaseHandler;

    @FXML
    private JFXTextField taskTextField;

    @FXML
    private JFXTextField descriptionTextField;

    @FXML
    private JFXButton saveTaskButton;

    @FXML
    private Label taskAddedLabel;

    @FXML
    private JFXButton todosButton;

    // colors for message label
    private static final String successColor = "#4BB543";
    private static final String failColor = "#FF0000";

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        saveTaskButton.setOnAction(event -> {
            Task task = new Task();

            String taskResult = taskTextField.getText();
            String description = descriptionTextField.getText();

            if(!taskResult.equals("") || !description.equals("")) {

                // setting userid task
                System.out.println(AddItemController.userId);
                task.setUserId(AddItemController.userId);

                // setting task from user input
                task.setTask(taskResult);
                // Setting the timestamp
                Calendar calendar = Calendar.getInstance();
                Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

                // setting datecreated
                task.setDateCreated(timestamp);

                // setting description from user input
                task.setDescription(description);

                databaseHandler.insertTask(task);

                taskAddedLabel.setVisible(true);
                taskAddedLabel.setTextFill(Color.valueOf(successColor));

                todosButton.setVisible(true);
                int taskNumber = 0;

                try {
                    taskNumber = databaseHandler.getAllTasks(AddItemController.userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                todosButton.setText("My todo's: (" + taskNumber + ")");
                todosButton.setOnAction(event1 -> {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../views/listTask.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                    stage.setResizable(false);
                });

                System.out.println("Task Successfully added!");

            } else {
                System.out.println("Something went wrong! Nothing added");
                taskAddedLabel.setText("Something went wrong! Please check the entries");
                taskAddedLabel.setVisible(true);
                taskAddedLabel.setTextFill(Color.valueOf(failColor));
            }

            taskTextField.setText("");
            descriptionTextField.setText("");

        });
    }

}
