package org.santiagolinares.model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AudioRecorderController {

    public AudioRecorderController( AudioRecorderService audioRecorderService) {
    }
    @PostMapping
    public void startRecording() {
         AudioRecorderService.startRecording();
    }

    @PostMapping
    public void stopRecording(){
        AudioRecorderService.stopRecording();
    }
}
