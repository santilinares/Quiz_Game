package org.santiagolinares;

import org.santiagolinares.model.AudioRecorderService;
import org.santiagolinares.model.ChatGPTService;
import org.santiagolinares.model.SpeechToTextService;

import java.util.List;

public class Main {

    private static final String AUDIO_FILE = "./src/data/AnswerRecordings/recording.wav";
    public static void main(String[] args) throws Exception {
        ChatGPTService.generateQuestions();
        List<String> questions = ChatGPTService.readQuestions();
        for (int i = 0; i < questions.size(); i++) {
            String question = questions.get(i);
            System.out.println(question);
            //AudioRecorderService.record();
            String answer = SpeechToTextService.transform(AUDIO_FILE);
            System.out.println(answer);
            System.out.println(ChatGPTService.checkAnswer(question, answer));
        }
    }
}
