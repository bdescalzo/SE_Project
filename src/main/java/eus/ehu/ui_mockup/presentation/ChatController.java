package eus.ehu.ui_mockup.presentation;

import atlantafx.base.util.Animations;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ChatController {

    @FXML
    private AnchorPane chatPane;

    @FXML
    private Label placeholder;

    @FXML
    void initialize() {
        Timeline welcomeAnimation = Animations.fadeInUp(chatPane, Duration.seconds(2));
        welcomeAnimation.playFromStart();
    }
}
