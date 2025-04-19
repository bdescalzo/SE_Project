package eus.ehu.TxikIA.presentation;


import eus.ehu.TxikIA.data_access.DBController;
import eus.ehu.TxikIA.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

public class LogoutController {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(LogoutController.class);

    @FXML
    private Button confirmLogoutButton;

    FxmlCacher fxmlCacher = new FxmlCacher("welcome-view.fxml");

    @FXML
    void confirmLogout(ActionEvent event) {
        String username = User.getUsername_static();
        User.setId_static(null);
        User.set_username_static(null);
        Pane root = (Pane) confirmLogoutButton.getScene().getRoot();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setWidth(600);
        stage.setHeight(400);
        log.warn("User "+username+" logged out!");
        fxmlCacher.loadContent(root);
    }

    @FXML
    void initialize() {
        confirmLogoutButton.setGraphic(new FontIcon(Material2AL.EXIT_TO_APP));
        confirmLogoutButton.setContentDisplay(ContentDisplay.RIGHT);
    }
}
