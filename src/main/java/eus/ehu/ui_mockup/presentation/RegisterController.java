package eus.ehu.ui_mockup.presentation;

import atlantafx.base.theme.Styles;
import eus.ehu.ui_mockup.business_logic.BInterface;
import eus.ehu.ui_mockup.business_logic.BusinessLogic;
import eus.ehu.ui_mockup.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import java.io.IOException;

public class RegisterController {

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private AnchorPane projectPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private StackPane modalPanePage;

    @FXML
    private PasswordField passField;

    @FXML
    private PasswordField passFieldConfirm;

    @FXML
    private Button registerButton;

    @FXML
    private TextField userField;

    private BInterface bInterface = new BusinessLogic();

    private FxmlCacher cacher = new FxmlCacher("projects/projectlist-view.fxml");

    private ModalFactory modal = new ModalFactory("register/register-error.fxml");
    @FXML
    void register(ActionEvent event) {
        if(passField.getText().equals(passFieldConfirm.getText())) {
            bInterface.createUser(userField.getText(), passField.getText());
            cacher.loadContent(registerPane);
        } else {
            registerError();
        }
    }

    @FXML
    void toWelcome(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            AnchorPane logonPane = fxmlLoader.load();
            registerPane.getChildren().setAll(logonPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        registerButton.setGraphic(new FontIcon(Material2AL.ACCOUNT_CIRCLE));
        registerButton.setContentDisplay(ContentDisplay.RIGHT);
        backButton.setGraphic(new FontIcon(Material2AL.ARROW_BACK_IOS));
        backButton.setContentDisplay(ContentDisplay.CENTER);

        modalPanePage.getChildren().add(modal.getModalPane());
    }

    void registerError(){
        modal.openModal(modalPanePage);
        registerButton.setDisable(true);
        /*
        userField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        passField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        passFieldConfirm.pseudoClassStateChanged(Styles.STATE_DANGER, false);
         */
        registerButton.setDisable(false);
    }


}

