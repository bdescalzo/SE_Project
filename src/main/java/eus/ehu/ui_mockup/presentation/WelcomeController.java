package eus.ehu.ui_mockup.presentation;

import atlantafx.base.util.Animations;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.IOException;

public class WelcomeController {


    @FXML
    private AnchorPane welcomePane;

    @FXML
    private AnchorPane logonPane;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private Button logonButton;

    @FXML
    private Label welcomeTitle;

    @FXML
    private Hyperlink registerButton;

    private final FxmlCacher login_loader = new FxmlCacher("logon/login-view.fxml");
    private final FxmlCacher register_loader = new FxmlCacher("register/register-view.fxml");

    @FXML
    void showLogin(ActionEvent event) {
        login_loader.loadContent(welcomePane);
    }

    @FXML
    void showRegister(ActionEvent event) {
       register_loader.loadContent(welcomePane);
    }

    @FXML
    void initialize() {
        logonButton.setGraphic(new FontIcon(Material2AL.ARROW_FORWARD));
        logonButton.setContentDisplay(ContentDisplay.RIGHT);
        Timeline welcomeAnimation = Animations.fadeIn(welcomePane, Duration.seconds(3));
        welcomeAnimation.playFromStart();
    }
}

