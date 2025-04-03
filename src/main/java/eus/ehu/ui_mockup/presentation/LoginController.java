package eus.ehu.ui_mockup.presentation;

import atlantafx.base.controls.Message;
import atlantafx.base.controls.ModalPane;
import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import eus.ehu.ui_mockup.business_logic.BInterface;
import eus.ehu.ui_mockup.business_logic.BusinessLogic;
import eus.ehu.ui_mockup.data_access.DBController;
import eus.ehu.ui_mockup.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;
import org.kordamp.ikonli.material2.Material2OutlinedAL;

import java.io.IOException;


public class LoginController {

    @FXML
    private AnchorPane projectPane;
    @FXML
    private AnchorPane loginPane;

    @FXML
    private StackPane modalPanePage;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    @FXML
    private Button notifBtn;

    ModalFactory newModal = new ModalFactory("logon/login-error.fxml");

    BInterface bizLog = new BusinessLogic();

    private final FxmlCacher project_loader = new FxmlCacher("projects/projectlist-view.fxml");

    @FXML
    void login(ActionEvent event) {

        if(bizLog.verifyUser(userField.getText(), passField.getText())) {
            project_loader.loadContent(loginPane);
        } else {
            userField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            passField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            loginError();
        }
    }


    @FXML
    void toWelcome(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            AnchorPane logonPane = fxmlLoader.load();
            loginPane.getChildren().setAll(logonPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void initialize() {
        loginButton.setGraphic(new FontIcon(Material2MZ.VPN_KEY));
        loginButton.setContentDisplay(ContentDisplay.RIGHT);
        backButton.setGraphic(new FontIcon(Material2AL.ARROW_BACK_IOS));
        backButton.setContentDisplay(ContentDisplay.CENTER);

        modalPanePage.getChildren().add(newModal.getModalPane());

    }

    void loginError(){

        newModal.openModal(modalPanePage);
        loginButton.setDisable(true);
        userField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        passField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        loginButton.setDisable(false);

    }
}
