package org.santiagolinares.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGPTService {

    private static final String FORMAT = "The JSON file should follow a specific structure to store a list of quiz questions. " +
            "Each question is represented as an individual set enclosed in curly braces. " +
            "Inside each set, there is a key-value pair, where the key is question, " +
            "and the associated value is the actual question. All these questions are organized as a list," +
            " surrounded by square brackets The name of this list should be: questions. " +
            "This list is also surrounded by curly braces. " +
            "This format ensures that the JSON file is well-structured, " +
            "making it easy to represent and organize multiple quiz questions, each " +
            "identified by the question key. Give me this format in plain text";
    private static final String FILE_PATH = "./src/data/questions/questions.json";
    private static final String URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("OPENAI_KEY"); // Get API key from environment variable
    private static final String MODEL = "gpt-3.5-turbo-1106";

    private static String chatGPT(String prompt) {

        try {
            HttpClient httpClient = HttpClient.newBuilder().build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                      "{\"model\": \"" + MODEL + "\"," +
                            " \"messages\": [" +
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

    public static String generateQuestions(){
        String prompt = " Generate 10 questions for a quiz game. Format it for a JSON file." +
                " Give me only the questions, without answers. " + FORMAT ;
        String response = chatGPT(prompt);
        saveAsJson(response);
        return response;
    }
    public static String checkAnswer(String question, String answer){
        String prompt = "Given this question: " + question + " Is this answer correct?: " + answer + ". " +
                "Reply shortly with Yes or No.";
        return chatGPT(prompt);
    }

    private static void saveAsJson(String jsonString){
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            JSONObject jsonObject = new JSONObject(new JSONTokener(jsonString));
            String formattedJsonString = jsonObject.toString(4);

            fileWriter.write(formattedJsonString);
            System.out.println("JSON file created successfully at: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static List<String> readQuestions(){
        List<String> questions = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONObject jsonObject = new JSONObject(content);

            JSONArray questionsArray = jsonObject.getJSONArray("questions");

            for (int i = 0; i < questionsArray.length(); i++){
                String question = questionsArray.getJSONObject(i).getString("question");
                questions.add(question);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return questions;
    }



}
