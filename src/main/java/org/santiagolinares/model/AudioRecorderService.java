package org.santiagolinares.model;

import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

@Service
public class AudioRecorderService {

    private static TargetDataLine targetLine;
    public static void startRecording() {
        //Specifying the audio format for the one needed in SpeechToText.java class
        AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);

        //Obtain and open
        try {
            DataLine.Info dataInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            if (!AudioSystem.isLineSupported(dataInfo)) {
                System.err.println("Not Supported");
            }

            targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);
            targetLine.open();

            JOptionPane.showMessageDialog(null, "Hit ok to start recording");
            System.out.println("Recording...");
            targetLine.start();

            Thread audioRecorderThread = new Thread(){
                @Override public void run(){
                    AudioInputStream recordingStream = new AudioInputStream(targetLine);
                    File outputFile = new File("./src/data/answerRecordings/recording.wav");
                    try {
                        AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }

                    System.out.println("Stopped recording");
                }
            };

            audioRecorderThread.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void stopRecording() {
        if (targetLine != null) {
            JOptionPane.showMessageDialog(null, "Hit ok to STOP recording");
            // Stops the recording after hitting ok.
            // The recording stops because the targetLine will be closed.
            targetLine.stop();
            targetLine.close();
        }
    }

}
