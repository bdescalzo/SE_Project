package eus.ehu.TxikIA.presentation;

import eus.ehu.TxikIA.domain.NormalizedRequest;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;
import eus.ehu.TxikIA.business_logic.BusinessLogic;
import java.net.URL;
import java.util.ArrayList;

import eus.ehu.TxikIA.llm_handler.APIRequestHandler;

public class ProjectController {

    private BusinessLogic bl = new BusinessLogic();
    @FXML
    private AnchorPane chatPane;

    @FXML
    private TextField chatInput;

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
    public void sendUserPrompt(ActionEvent event) {
        // Get the user message, and send it to the chat interface
        String userMessage = chatInput.getText();
        chatInput.clear();
        WebEngine details = chatWindow.getEngine();
        details.executeScript("addUserMessage('" + userMessage.replace("'", "\\'") + "');");

        // Create a task to handle the LLM request (to avoid freezing the app)
        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                // Normalize the LLM request
                System.out.println("We are normalizing the request");
                NormalizedRequest response = bl.getNormalizedRequest(userMessage);
                System.out.println("Normalized request: " + response.toString());
                String answer;
                // TODO: Implement jumping to other features (quiz and question)
                // If it is an explanation request, get the explanation
                switch(response.getQuestion_type()) {
                    case "EXPLANATION":
                        System.out.println("Thinking cap on!");
                        answer = bl.getExplanation(response);
                        break;
                    default:
                        answer = "Sorry, I'm not sure how to help. Try again!";
                }

                System.out.println("Thinking cap off!");
                return answer;
            }
        };

        task.setOnSucceeded(workerStateEvent -> {
            String answer = task.getValue();
            // Add the LLM message to the interface
            System.out.println("We got this answer: " + answer);
            details.executeScript("addLLMMessage('" + answer.replace("'", "\\'") + "');");
        });

        new Thread(task).start();
    }

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
