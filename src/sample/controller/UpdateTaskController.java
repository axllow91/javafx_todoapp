package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateTaskController {
    @FXML
    private JFXTextField updateTaskField;

    @FXML
    private JFXTextField updateDescriptionField;

    @FXML
    public JFXButton updateTaskButton;

    @FXML
    void initialize() {
    }

    void setTaskField(String task) {
        updateTaskField.setText(task);
    }

    public String getTask() {
        return updateTaskField.getText().trim();
    }

    void setUpdateDescriptionField(String description) {
        updateDescriptionField.setText(description);
    }

    public String getDescription() {
        return updateDescriptionField.getText().trim();
    }

    void refreshList() throws SQLException {

        System.out.println("Calling refresh list!");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/views/listTask.fxml"));

        try {

            loader.load();

            ListTaskController listTaskController = loader.getController();
            listTaskController.refreshList();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
