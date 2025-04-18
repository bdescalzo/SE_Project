package eus.ehu.TxikIA.business_logic;

import eus.ehu.TxikIA.data_access.DBController;
import eus.ehu.TxikIA.domain.*;
import eus.ehu.TxikIA.llm_handler.APIRequestHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BusinessLogic implements BInterface{

    static DBController db = new DBController();

    @Override
    public boolean verifyUser(String username, String password) {
        return db.verifyUser(username, password);
    }

    @Override
    public void createUser(String username, String password) {
        db.createUser(username, password);
    }

    @Override
    public boolean firstLogin(UUID user_id) {
        return db.firstLogin(user_id);
    }

    @Override
    public List<Project> retrieveProjects(UUID id) {
        return db.getProjects(id);
    }

    @Override
    public boolean createProject(UUID user_id, Project project) {
        return db.storeProject(user_id, project);
    }

    public boolean editProject(UUID id, String name, String description){
        return db.editProject(id, name, description);
    }

    public boolean deleteProject(UUID user_id, UUID project_id) {
        return db.deleteProject(user_id, project_id);
    }

    public NormalizedRequest getNormalizedRequest(String prompt) {
        db.addMessage(new Message(prompt, "", true)); // Add the user message to the chat history
        return APIRequestHandler.normalizePrompt(prompt, null);
    }

    public String getExplanation(NormalizedRequest request) {
        // Build string containing the past context
        StringBuilder context = new StringBuilder();
        List<Message> messages = getMessages(); // Get all messages from the current project
        for (Message message : messages) {
            if (!message.isUserMessage()) context.append("INTERACTION: ").append(message.getSummary()).append("\n");
        }

        // Call LLM to get an explanation from the request and the past context
        ExplanationOutput output = APIRequestHandler.getExplanation(request, context.toString());

        // Store the answer in DB
        db.addMessage(new Message(output.get_full_answer(), output.get_answer_context(), false));
        return output.get_full_answer();
    }

    public List<Message> getMessages() {
        // Get the current project
        Project project = db.find(Project.class, Project.getCurrent_UUID());

        // Retrieve  all messages from current project
        List<Message> messages = new ArrayList<>();
        messages.addAll(project.getChat().getMessages());

        return messages;
    }

    public String getErrorMessage() {
        db.addMessage(new Message("Sorry, I'm not sure how to help. Try again!", "", false)); // Add the assistant message to the chat history
        return "Sorry, I'm not sure how to help. Try again!";
    }
}
