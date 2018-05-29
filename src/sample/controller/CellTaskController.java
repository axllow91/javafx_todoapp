package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class CellTaskController extends JFXListCell<Task> {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label taskLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView deleteImageButton;

    @FXML
    private ImageView listUpdateButton;

    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        //
    }

    @Override
    protected void updateItem(Task mTask, boolean empty) {

        databaseHandler = new DatabaseHandler();

        super.updateItem(mTask, empty);

        if (empty || mTask == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                try {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/sample/views/cellTask.fxml"));
                    fxmlLoader.setController(this);

                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            taskLabel.setText(mTask.getTask());
            descriptionLabel.setText(mTask.getDescription());
            dateLabel.setText(mTask.getDateCreated().toString());

            // fetching the taskId from the task Class
            int taskId = mTask.getTaskId();

            listUpdateButton.setOnMouseClicked(event -> {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/views/updateTask.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);

                // get the controller
                UpdateTaskController updateTaskController = loader.getController();
                updateTaskController.setTaskField(mTask.getTask());
                updateTaskController.setUpdateDescriptionField(mTask.getDescription());

                updateTaskController.updateTaskButton.setOnAction(event1 -> {

                    Calendar calendar = Calendar.getInstance();
                    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
                    System.out.println(timestamp.toString());
                    System.out.println("Task: " + updateTaskController.getTask());
                    System.out.println("Description: " + updateTaskController.getDescription());

                    try {
                        System.out.println("taskId: " + mTask.getTaskId());

                        databaseHandler.updateTask(timestamp, updateTaskController.getTask(),
                                                updateTaskController.getDescription(), mTask.getTaskId());

                        // update list controller
                        updateTaskController.refreshList();

                        System.out.println("Task successfully updated!");


                    } catch (SQLException e) {
                        e.printStackTrace();

                    }
                    updateTaskController.updateTaskButton.getScene().getWindow().hide();
                });

                stage.show();
            });


            deleteImageButton.setOnMouseClicked(event -> {

                // remove task from DB
                databaseHandler.deleteTask(AddItemController.userId, taskId);

                // remove the item that is clicked
                getListView().getItems().remove(getItem());
            });

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }
}
