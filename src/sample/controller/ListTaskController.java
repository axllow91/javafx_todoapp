package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import sample.database.Const;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;


public class ListTaskController {

    @FXML
    private JFXListView<Task> listTasks;

    @FXML
    private JFXTextField taskListTextField;

    @FXML
    private JFXTextField descriptionListTextField;

    @FXML
    private JFXButton saveTaskListButton;

    @FXML
    private ImageView refreshImageView;

    private ObservableList<Task> myTasks;
    private ObservableList<Task> refreshTasks;

    private DatabaseHandler databaseHandler;


    @FXML
    void initialize() throws SQLException {

        // initiate my observable list of <Task>
        myTasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {

            Task task = new Task();
            task.setTaskId(resultSet.getInt(Const.TASKS_TASKID));
            task.setTask(resultSet.getString(Const.TASKS_TASK));
            task.setDateCreated(resultSet.getTimestamp(Const.TASKS_DATECREATED));
            task.setDescription(resultSet.getString(Const.TASKS_DESCRIPTION));

            myTasks.add(task);
        }

        listTasks.setItems(myTasks);
        listTasks.setCellFactory(CellTaskController -> new CellTaskController());

        // refresh
        refreshImageView.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        saveTaskListButton.setOnAction(event -> addNewTask());
    }

    private void addNewTask() {

        if (!taskListTextField.getText().equals("") ||
                !descriptionListTextField.getText().equals("")) {
            Task newTask = new Task();
            String task = taskListTextField.getText().trim();
            String description = descriptionListTextField.getText().trim();

            Calendar calendar = Calendar.getInstance();
            Timestamp timeStamp = new Timestamp(calendar.getTimeInMillis());

            // add to the new task object task and description
            newTask.setUserId(AddItemController.userId);
            newTask.setTask(task);
            newTask.setDescription(description);
            newTask.setDateCreated(timeStamp);

            // insert my new task into db!
            databaseHandler.insertTask(newTask);

            // make them blank
            taskListTextField.setText("");
            descriptionListTextField.setText("");

            //
            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void refreshList() throws SQLException {

        System.out.println("RefreshList in ListTaskController called!");

        // initiate my observable list of <Task>
        refreshTasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {

            Task task = new Task();
            task.setTaskId(resultSet.getInt(Const.TASKS_TASKID));
            task.setTask(resultSet.getString(Const.TASKS_TASK));
            task.setDateCreated(resultSet.getTimestamp(Const.TASKS_DATECREATED));
            task.setDescription(resultSet.getString(Const.TASKS_DESCRIPTION));

            refreshTasks.addAll(task);
        }

        listTasks.setItems(myTasks);
        listTasks.setCellFactory(CellTaskController -> new CellTaskController());

    }

}

