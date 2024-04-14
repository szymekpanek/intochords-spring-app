package pl.wszib.edu.pl.intochordsspringapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class IntervalGameServices {
    @Autowired
    SoundDB soundDB;
    Map<String, String> intervalsMap = new LinkedHashMap<>();
    public void Intervals_Map(){
        intervalsMap.put("U", "Unison");
        intervalsMap.put("m2", "Minor second");
        intervalsMap.put("M2", "Major second");
        intervalsMap.put("m3", "Minor third");
        intervalsMap.put("M3", "Major third");
        intervalsMap.put("P4", "Perfect fourth");
        intervalsMap.put("T", "Triton");
        intervalsMap.put("P5", "Perfect fifth");
        intervalsMap.put("m6", "Minor sixth");
        intervalsMap.put("M6", "Major sixth");
        intervalsMap.put("m7", "Minor seventh");
        intervalsMap.put("M7", "Major seventh");
        intervalsMap.put("8", "Octave");
    }

    public void printIntervals() {
        for (String interval : intervalsMap.values()) {
            System.out.println(interval);
        }
    }

    public void showIntervalsMap() {
        intervalsMap.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .forEach(System.out::println);
    }

    public String getRandomKey() {
        return intervalsMap.keySet()
                .stream()
                .skip(new Random().nextInt(intervalsMap.size()))
                .findFirst()
                .orElse(null);
    }

    public void playSound (String note) throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        if (soundDB.getSoundMap().containsKey(note)) {

            File soundFileName = new File(soundDB.getSoundFileName(note));
            System.out.println("Odtwarzam dźwięk: " + soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFileName);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

        } else System.out.println("Wrong note");
    }


//    public void playSound(String fileName) {
//        String filePath = "path/to/sounds/" + fileName + ".mp3"; // Ścieżka do plików dźwiękowych MP3
//
//        try {
//            InputStream is = new BufferedInputStream(new FileInputStream(filePath));
//            Player player = new Player(is);
//            player.play();
//        } catch (IOException | JavaLayerException e) {
//            e.printStackTrace();
//        }
//    }

}
