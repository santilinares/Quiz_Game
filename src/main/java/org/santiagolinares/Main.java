package org.santiagolinares;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import java.net.HttpURLConnection;

public class Main {
    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            //Setting the connection with the API
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            //Request
            String request = "{\"model\": \"" + model + "\", \"messages\":" +
                    " " + "[{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(request);
            writer.flush();
            writer.close();

            //Response
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            //calls the method to extract the message
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);
    }

    public static void main(String[] args) {
        String message = "Imagine you are the presenter of a Quiz Game. Make a question for the players." +
                " Answer me directly with the question, without and introduction ";
        System.out.println(chatGPT(message));
    }
}
