package eus.ehu.TxikIA.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import java.net.URL;

public class ProjectController {

    @FXML
    private AnchorPane chatPane;

    @FXML
    private Label placeholder;

    @FXML
    private TabPane mainBar;

    @FXML
    private Tab chatTab;

    @FXML
    private Tab quizTab;

    @FXML
    private Tab exerciseTab;

    @FXML
    private Button sideBarButton;

    @FXML
    private Button addFileButton;

    @FXML
    private Button sendButton;

    @FXML
    private RadioButton opt1;

    @FXML
    private RadioButton opt2;

    @FXML
    private RadioButton opt3;

    @FXML
    private RadioButton opt4;

    @FXML
    private ToggleGroup chatToggleGroup = new ToggleGroup();

    @FXML
    private WebView chatWindow;

    @FXML
    void initialize() {
        sideBarButton.setGraphic(new FontIcon(Material2AL.DEHAZE));
        addFileButton.setGraphic(new FontIcon(Material2AL.FILE_UPLOAD));
        sendButton.setGraphic(new FontIcon(Material2MZ.SEND));

        chatTab.setGraphic(new FontIcon(Material2AL.CHAT));
        quizTab.setGraphic(new FontIcon(Material2MZ.SCHOOL));
        exerciseTab.setGraphic(new FontIcon(Material2AL.CREATE));

        opt1.setToggleGroup(chatToggleGroup);
        opt2.setToggleGroup(chatToggleGroup);
        opt3.setToggleGroup(chatToggleGroup);
        opt4.setToggleGroup(chatToggleGroup);

        WebEngine details = chatWindow.getEngine();
        URL url = this.getClass().getResource("webview"+"/"+ "index.html");
        details.load(url.toString());


    }
}
