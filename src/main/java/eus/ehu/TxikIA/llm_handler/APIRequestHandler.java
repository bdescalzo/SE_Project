package eus.ehu.TxikIA.llm_handler;

import eus.ehu.TxikIA.domain.ExplanationOutput;
import eus.ehu.TxikIA.domain.NormalizedRequest;
import eus.ehu.TxikIA.domain.SystemPrompts;
import okhttp3.*;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;

import java.io.FileReader;
import java.util.concurrent.TimeUnit;

public class APIRequestHandler {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(APIRequestHandler.class);

    /* In a real set-up, these shouldn't be hardcoded */
    private static final String GEMINI_API_KEY = "INSERT GEMINI KEY";
    private static final String DEEPSEEK_API_KEY = "INSERT DEEPSEEK KEY";
    static SystemPrompts systemPrompts = loadSystemPrompts();;

    private static SystemPrompts loadSystemPrompts() {
        // Path to the system_prompts.json file
        String filePath = "src/main/java/eus/ehu/TxikIA/llm_handler/system_prompts.json";

        // Create a Gson instance
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            // Deserialize JSON into SystemPrompts object
            SystemPrompts result = gson.fromJson(reader, SystemPrompts.class);
            log.info("System prompts for LLM have been successfully loaded.");
            return result;
        } catch (Exception e) {
            log.error("System prompts for LLM could not be successfully loaded.");
            return new SystemPrompts();
        }
    }

    public static NormalizedRequest normalizePrompt(String prompt, String doc) {

        String userPrompt = prompt + (doc != null ? (" CONTENT_FROM_ATTACHED_DOCUMENT" + doc) : " NO_DOCUMENT_ATTACHED");

        Gson gson = new Gson();

        // Call the Deepseek API with the normalization system prompt
        String normalizedPrompt = deepseekRequest(systemPrompts.get("normalization_prompt"), userPrompt, "reasoner");

        // Deserialize the response into NormalizationLLMRequest object
        return gson.fromJson(normalizedPrompt, NormalizedRequest.class);
    }

    public static ExplanationOutput getExplanation(NormalizedRequest request, String past_context) {
        // Call the Deepseek API with the explanation system prompt
        // TODO: Implement code generation on chat
        String userPrompt = request.getFormalized_request() + " PAST_CONTEXT " + past_context + " GENERATED_CODE " +" No code generated";
        log.info("Asking LLM for prompt: " + userPrompt);
        String explanation = deepseekRequest(systemPrompts.get("explanation_prompt"), userPrompt, "reasoner");

        // Deserialize the response into ExplanationOutput object
        Gson gson = new Gson();
        return gson.fromJson(explanation, ExplanationOutput.class);
    }

    // Main only for testing purposes
    public static void main(String[] args) {
        NormalizedRequest normalizedRequest = normalizePrompt("Do you sell drugs?", null); // Troll request

        System.out.println("This is the output:\n" + normalizedRequest.toString());
    }

    /**
     *
     * @param systemPrompt The system prompt to be used in the request.
     * @param userPrompt The user prompt (that is, the request itself).
     * @param modelType Either 'reasoner' or 'chat'.
     * @return The string returned by the Deepseek API
     * This method makes a request to the Deepseek API, and returns the result as a string. If the request asks for a JSON, the method removes the leading ```json and trailing ``` characters that Deepseek adds to it.
     */
    private static String deepseekRequest(String systemPrompt, String userPrompt, String modelType) {
        // Base URL for the Deepseek API
        String apiUrl = "https://api.deepseek.com/chat/completions";

        // Create the JSON payload using Gson
        JsonObject payload = new JsonObject();
        payload.addProperty("model", "deepseek-chat");
        payload.addProperty("stream", false);

        JsonArray messages = new JsonArray();

        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", systemPrompt);
        messages.add(systemMessage);

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", userPrompt);
        messages.add(userMessage);

        payload.add("messages", messages);

        // Custom OkHttpClient: Otherwise, the request times out
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS) // Increase connection timeout
                .readTimeout(120, TimeUnit.SECONDS)    // Increase read timeout
                .writeTimeout(120, TimeUnit.SECONDS)   // Increase write timeout
                .build();

        // Build the request
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + DEEPSEEK_API_KEY.trim())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(payload.toString(), MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            log.info("Trying to contact with Deepseek API");
            if (response.isSuccessful() && response.body() != null) {
                log.info("Successful response");
                // Parse the response body
                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                // Extract the "content" field from the first choice
                String content = jsonResponse
                        .getAsJsonArray("choices")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("message")
                        .get("content")
                        .getAsString();
                String result = content.replaceAll("```json", "").replaceAll("```", "").trim();

                log.info("Trimmed API answer: " + result);
                // The Deepseek API adds leading ```json and trailing ``` to the output, but we can't use it that way in gson, so it has to be trimmed
                return result;
            } else {
                log.error("API Error: " + response.code() + " - " + response.message());
                if (response.body() != null) {
                    log.error("Response Body in API error: " + response.body().string());
                }
                throw new Exception("Unexpected response: " + response);
            }
        } catch (Exception e) {
            log.error("Exception occurred while trying to contact Deepseek API");
            return null; // Return null in case of an error
        }
    }
}
