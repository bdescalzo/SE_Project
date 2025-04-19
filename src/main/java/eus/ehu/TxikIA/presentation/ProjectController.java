package eus.ehu.TxikIA.presentation;

import eus.ehu.TxikIA.data_access.DBController;
import eus.ehu.TxikIA.domain.Message;
import eus.ehu.TxikIA.domain.NormalizedRequest;
import eus.ehu.TxikIA.domain.Project;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.logging.log4j.LogManager;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;
import eus.ehu.TxikIA.business_logic.BusinessLogic;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.TxikIA.llm_handler.APIRequestHandler;

public class ProjectController {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ProjectController.class);

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
        // Only proceed if the user has written something
        if (chatInput.getText().isEmpty()) {
            log.error("User has tried to send empty message.");
            return;
        }

        // Get the user message, and send it to the chat interface
        String userMessage = chatInput.getText();
        chatInput.clear();
        sendButton.setDisable(true); // Prevent user messages until the LLM answer is received
        WebEngine details = chatWindow.getEngine();
        addMessageToChat(userMessage, false);


        // Create a task to handle the LLM request (to avoid freezing the app)
        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                // Normalize the LLM request
                System.out.println("We are normalizing the request");
                NormalizedRequest response = bl.getNormalizedRequest(userMessage);
                String answer;
                // TODO: Implement jumping to other features (quiz and question)
                // If it is an explanation request, get the explanation
                switch(response.getQuestion_type()) {
                    case "EXPLANATION":
                        answer = bl.getExplanation(response);
                        break;
                    default:
                        answer = bl.getErrorMessage();
                }

                return answer;
            }
        };

        task.setOnSucceeded(workerStateEvent -> {
            sendButton.setDisable(false);
            String answer = task.getValue();
            // Add the LLM message to the interface
            System.out.println("We got this answer: " + answer);
            addMessageToChat(answer, true);
        });

        task.setOnFailed(workerStateEvent -> {
            sendButton.setDisable(false);
            Throwable exception = task.getException();
            log.error("Error while sending the request to the LLM: {}", exception.getMessage());
            addMessageToChat("Error: " + exception.getMessage(), true);
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
        URL url = this.getClass().getResource("webview"+"/"+ "chat-view.html");
        details.load(url.toString());

        // Load the chat history when the web engine is ready
        details.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadMessages();
            }
        });
    }

    void loadMessages() {
        List<Message> messages = bl.getMessages();

        // Load message history into UI
        for (Message message : messages) {
            System.out.println("MESSAGE: " + message.getContent());
            boolean isUserMessage = message.isUserMessage();
            String messageContent = message.getContent();
            addMessageToChat(messageContent, !isUserMessage);
        }
    }

    /**
     *
     * @param message The message to add in the chat
     * @param llm True if the message is from the LLM, false if it is from the user
     * Writes the message on the screen, by sending it to the HTML view
     */
   void addMessageToChat(String message, boolean llm) {
        WebEngine details = chatWindow.getEngine();

        // Sanitize the message first
        String sanitizedMessage = message.replace("\\","\\\\")
                .replace("'", "\\'")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

        if (llm) {
            details.executeScript("addLLMMessage('" + sanitizedMessage + "');");
            log.info("LLM message added to chat");
        }
        else {
            details.executeScript("addUserMessage('" + sanitizedMessage + "');");
            log.info("User message added to chat");
        }
    }
}
