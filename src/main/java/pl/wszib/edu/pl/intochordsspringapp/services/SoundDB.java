package pl.wszib.edu.pl.intochordsspringapp.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Component
public class SoundDB {

    private Map<String, String> soundMap;

    private void addSound(String note, String soundFileName) {
        soundMap.put(note, soundFileName);
    }

    public SoundDB() {
        soundMap = new HashMap<>();
        String path = "src/main/resources/static/wav_files/";
        addSound("C", path + "c.wav");
        addSound("C#", path + "c#.wav");
        addSound("D", path + "d.wav");
        addSound("D#", path + "d#.wav");
        addSound("E", path + "e.wav");
        addSound("F", path + "f.wav");
        addSound("F#", path + "f#.wav");
        addSound("G", path + "g.wav");
        addSound("G#", path + "g#.wav");
        addSound("A", path + "A.wav");
        addSound("BB", path + "bb.wav");
        addSound("B", path + "b.wav");
        addSound("C2", path + "c2.wav");
    }

    public void printSounds() {
        for (String sounds : soundMap.values()) {
            soundMap.get(sounds);
        }
    }

    public Map<String, String> getSoundMap() {
        return soundMap;
    }
    public String getSoundFileName(String note) {
        return soundMap.get(note);
    }
    public String getNote(){
        return soundMap.keySet().toString();
    }
}
