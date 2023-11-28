package org.santiagolinares;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatGPTConfiguration {

    private static final String URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("OPENAI_KEY"); // Get API key from environment variable
    private static final String MODEL = "gpt-3.5-turbo-1106";
    private static final String system = "You are the presenter of a Quiz Game. You just make questions and check the " +
            "correctness of the answers by saying yes or no";
    public static String generateQuestions() {

        String prompt = "Make a question. Without introducing yourself.";

        try {
            HttpClient httpClient = HttpClient.newBuilder().build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                      "{\"model\": \"" + MODEL + "\"," +
                            " \"messages\": [" +
                              "{\"role\": \"system\", \"content\":  \"" + system + "\"}," +
                              "{\"role\": \"user\", \"content\": \"" + prompt + "\"}" +
                              "]}"))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("API call failed with status code: " + response.statusCode());
            }

            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject lastChoice = choices.getJSONObject(0);
            JSONObject message = lastChoice.getJSONObject("message");
            return message.getString("content");

        } catch (IOException | InterruptedException | URISyntaxException e) {
            System.err.println("An error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String checkAnswer(String question, String answer){
        String prompt = "";
        return "";
    }


}
