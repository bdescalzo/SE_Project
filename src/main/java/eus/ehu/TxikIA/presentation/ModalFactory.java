package eus.ehu.TxikIA.presentation;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

public class ModalFactory {

    @FXML
    private ModalPane modalPane;

    @FXML
    private Stage stage;

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

        stage = (Stage) pane.getScene().getWindow();

        int prev_x = stage.widthProperty().intValue();
        int prev_y = stage.heightProperty().intValue();

        stage.setMinWidth(620);
        stage.setMinHeight(760);

        int x = pane.prefWidthProperty().multiply(0.65).intValue();
        int y = pane.prefHeightProperty().multiply(0.75).intValue();



        var dialog = new Dialog(x,y);


        var closeBtn = new Button("Close", new FontIcon(Material2AL.CLOSE));
        closeBtn.setOnAction(evt -> closeModal(pane, prev_x, prev_y));
        closeBtn.getStyleClass().addAll(Styles.DANGER, Styles.BUTTON_OUTLINED);
        closeBtn.setContentDisplay(ContentDisplay.RIGHT);


        cacher.loadModal(dialog);
        dialog.getChildren().add(closeBtn);
        modalPane.show(dialog);

    }

    @FXML
    void closeModal(StackPane pane, int x, int y) {
        modalPane.hide(true);
        stage.setMinHeight(520);
        stage.setMinWidth(360);
        stage.setWidth(x);
        stage.setHeight(y);
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
