package sample.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.animations.Shaker;

import java.io.IOException;

public class AddItemController {
    @FXML
    private ImageView addButton;

    @FXML
    private Label noTasksLabel;

    @FXML
    private AnchorPane rootAnchorPane;

    static int userId;

    @FXML
    void initialize() {

        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Shaker buttonShaker = new Shaker(addButton);
            buttonShaker.shake();

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), addButton);
            FadeTransition labelTransition = new FadeTransition(Duration.millis(3000), noTasksLabel);

            // Remove
            System.out.println("Add button works!");
            addButton.relocate(-1, 20);
            noTasksLabel.relocate(0, 0);

            addButton.setOpacity(0);
            noTasksLabel.setOpacity(0);

            fadeTransition.setFromValue(1f);
            fadeTransition.setToValue(0f);
            fadeTransition.setCycleCount(2);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();

            labelTransition.setFromValue(1f);
            labelTransition.setToValue(0f);
            labelTransition.setCycleCount(2);
            labelTransition.setAutoReverse(false);
            labelTransition.play();


            try {
                AnchorPane formAnchorPane = FXMLLoader.load(
                        getClass().getResource("/sample/views/addItemForm.fxml"));

//                AddItemFormController addItemFormController = new AddItemFormController();
//                addItemFormController.setUserId(getUserId());

                AddItemController.userId = getUserId();

                FadeTransition rootTransition = new FadeTransition(Duration.millis(2000), formAnchorPane);
                rootTransition.setFromValue(0f);
                rootTransition.setToValue(1f);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();

                rootAnchorPane.getChildren().setAll(formAnchorPane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    void setUserId(int userId) {
        this.userId = userId;
    }

    int getUserId() {
        return userId;
    }
}
