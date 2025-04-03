package eus.ehu.ui_mockup.presentation;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.theme.Styles;
import eus.ehu.ui_mockup.domain.Project;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

public class ModalFactory {

    @FXML
    private ModalPane modalPane;

    private FxmlCacher cacher;

    public ModalFactory(String fxmlFile) {
        modalPane = new ModalPane();
        modalPane.setAlignment(Pos.CENTER);
        modalPane.usePredefinedTransitionFactories(null);
        modalPane.setPersistent(true);
        cacher = new FxmlCacher(fxmlFile);
    }
    @FXML
    void openModal(StackPane pane) {

        pane.setVisible(true);


        var dialog = new Dialog(240, 240);

        var closeBtn = new Button("Close", new FontIcon(Material2AL.CLOSE));
        closeBtn.setOnAction(evt -> closeModal(pane));
        closeBtn.getStyleClass().addAll(Styles.DANGER, Styles.BUTTON_OUTLINED);
        closeBtn.setContentDisplay(ContentDisplay.RIGHT);


        cacher.loadContent(dialog);
        dialog.getChildren().add(closeBtn);
        modalPane.show(dialog);
    }

    @FXML
    void closeModal(StackPane pane) {
        modalPane.hide(true);
        pane.setVisible(false);
    }


    public static class Dialog extends VBox {

        public Dialog(int width, int height) {
            super();
            setSpacing(10);
            setAlignment(Pos.CENTER);
            setMinSize(width, height);
            setMaxSize(width, height);
            setStyle("-fx-background-color: -color-bg-default;");
        }
    }

    public ModalPane getModalPane() {
        return modalPane;
    }
}
