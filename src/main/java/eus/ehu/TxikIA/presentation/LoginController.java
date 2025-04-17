package eus.ehu.TxikIA.presentation;


import atlantafx.base.theme.Styles;
import eus.ehu.TxikIA.business_logic.BInterface;
import eus.ehu.TxikIA.business_logic.BusinessLogic;
import eus.ehu.TxikIA.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.apache.logging.log4j.LogManager;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;


public class LoginController {

    @FXML
    private GridPane loginPane;

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

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(LoginController.class);

    ModalBuilder newModal = new ModalBuilder("logon/login-error.fxml");

    BInterface bizLog = new BusinessLogic();

    private final FxmlCacher project_loader = new FxmlCacher("projects/projectlist-view.fxml");

    private final FxmlCacher welcome_loader = new FxmlCacher("welcome-view.fxml");

    @FXML
    void login(ActionEvent event) {
        log.info("Trying to log in as User "+userField.getText()+"..........");
        if(bizLog.verifyUser(userField.getText(), passField.getText())) {
            log.info("\033[0;32mUser " + User.getUsername_static()+" correctly logged in!! \033[0m");
            project_loader.loadContent(loginPane);
        } else {
            userField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            passField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            loginError();
        }
    }


    @FXML
    void toWelcome(ActionEvent event) {
        welcome_loader.loadContent(loginPane);
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
