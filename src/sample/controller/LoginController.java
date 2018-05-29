package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.animations.Shaker;
import sample.database.Const;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private JFXTextField usernameTextField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton signupButton;

    private DatabaseHandler databaseHandler = new DatabaseHandler();

    private int userid;

    @FXML
    void initialize() {

        loginButton.setOnAction(event -> {

            String loginText = usernameTextField.getText().trim();
            String loginPass = passwordField.getText().trim();

            User user = new User();
            user.setUsername(loginText);
            user.setPassword(loginPass);

            ResultSet userRow = databaseHandler.getUser(user);
            int counter = 0;

            try {
                while (userRow.next()) {
                    counter++;
                    String name = userRow.getString(Const.USERS_FIRSTNAME);
                    userid = userRow.getInt(Const.USERS_USERID);
                    System.out.println("Welcome, " + name + "!");
                }

                if(counter == 1) {
                    showAddItemScreen();
                } else {
                    // create the animation(shake animation) for the usernameTextField | passwordField
                    // if credentials fail
                    Shaker shaker1 = new Shaker(usernameTextField);
                    shaker1.shake();
                    Shaker shaker2 = new Shaker(passwordField);
                    shaker2.shake();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        signupButton.setOnAction(event -> {
            // Take users to signup screen
            loginButton.getScene().getWindow().hide();
            // load the
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/signup.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        });
    }

    private void showAddItemScreen() {

            // Take users to addItem screen
            loginButton.getScene().getWindow().hide();

            // Load addItem fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/addItem.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);

            AddItemController addItemController = loader.getController();
            addItemController.setUserId(userid);

            stage.showAndWait();
    }


}
