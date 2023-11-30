package org.santiagolinares.model;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/chatgpt")
public class ChatGPTController {

    public ChatGPTController(ChatGPTService chatGPTService){
    }
    @GetMapping("/generate-questions")
    public String generateQuestions(){
        return ChatGPTService.generateQuestions();
    }

    @GetMapping("check-answer")
    public String checkAnswer(@RequestBody String question, String answer) {
        return ChatGPTService.checkAnswer(question, answer);
    }

    @GetMapping("(read-questions")
    public List<String> readQuestions() {
        return ChatGPTService.readQuestions();
    }

}
