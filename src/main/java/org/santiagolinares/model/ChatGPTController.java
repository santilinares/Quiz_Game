package org.santiagolinares.model;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/chatgpt")
public class ChatGPTController {

    public ChatGPTController(ChatGPTService chatGPTService){
    }
    @PostMapping("/generate-questions")
    public String generateQuestions(){
        return ChatGPTService.generateQuestions();
    }

    @PostMapping("check-answer")
    public String checkAnswer(@RequestBody String question, String answer) {
        return ChatGPTService.checkAnswer(question, answer);
    }

    @PostMapping("(read-questions")
    public List<String> readQuestions() {
        return ChatGPTService.readQuestions();
    }

}
