package org.santiagolinares.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/speech-to-text")
public class SpeechToTextController {
    @Autowired
    public SpeechToTextController(SpeechToTextService speechToTextService) {
    }

    @GetMapping("/transform")
    public String transformAudio(@RequestBody String fileName) throws Exception {
        return SpeechToTextService.transform(fileName);
    }
}
