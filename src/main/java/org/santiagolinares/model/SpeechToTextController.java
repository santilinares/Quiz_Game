package org.santiagolinares.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/speech-to-text")
public class SpeechToTextController {
    @Autowired
    public SpeechToTextController(SpeechToTextService speechToTextService) {
    }

    @PostMapping("/transform")
    public String transformAudio(@RequestBody String fileName) throws Exception {
        return SpeechToTextService.transform(fileName);
    }
}
