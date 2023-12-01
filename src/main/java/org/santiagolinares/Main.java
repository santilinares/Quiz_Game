package org.santiagolinares;

import java.util.List;

public class Main {

    private static final String AUDIO_FILE = "./src/data/AnswerRecordings/recording.wav";
    public static void main(String[] args) throws Exception {
        ChatGPTConfiguration.generateQuestions();
        List<String> questions = ChatGPTConfiguration.readQuestions();
        String question = questions.get(0);
        System.out.println(question);
        AudioRecorder.record();
        String answer = SpeechToText.transform(AUDIO_FILE);
        System.out.println(answer);
        System.out.println(ChatGPTConfiguration.checkAnswer(question, answer));
    }
}
