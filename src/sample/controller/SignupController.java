package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;

public class SignupController {
    @FXML
    private JFXTextField firstNameTextField;

    @FXML
    private JFXTextField lastNameTextField;

    @FXML
    private JFXTextField userNameTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private JFXTextField locationTextField;

    @FXML
    private JFXCheckBox maleCheckBox;

    @FXML
    private JFXCheckBox femaleCheckBox;

    @FXML
    private JFXButton signupFormButton;

    @FXML
    void initialize() {
        signupFormButton.setOnAction(event -> {
            createUser();

            signupFormButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent pane = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.showAndWait();

        });
    }

    private void createUser() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        String location = locationTextField.getText();

        String gender;
        if (maleCheckBox.isSelected()) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        User user = new User(firstName, lastName, userName, password, location, gender);

        databaseHandler.signupUser(user);
    }
}
