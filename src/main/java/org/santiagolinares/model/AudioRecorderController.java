package org.santiagolinares.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/audio-recorder")
public class AudioRecorderController {

    public AudioRecorderController( AudioRecorderService audioRecorderService) {
    }
    @GetMapping("/start-recording")
    public void startRecording() {
         AudioRecorderService.startRecording();
    }

    @GetMapping("/stop-recording")
    public void stopRecording(){
        AudioRecorderService.stopRecording();
    }
}
