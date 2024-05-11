package pl.wszib.edu.pl.intochordsspringapp.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.wszib.edu.pl.intochordsspringapp.model.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Getter
@Setter
@AllArgsConstructor
public class SoundDB {
    private List<Sound> sounds = new ArrayList<>();
    private String path = "/wav_files/";
    private String prefix = "is";
    public SoundDB(){
        sounds.add(new Sound(path+ "c.wav", "C"));
        sounds.add(new Sound(path +"c"+prefix+".wav", "C#"));
        sounds.add(new Sound(path +"d.wav", "D"));
        sounds.add(new Sound(path +"d"+prefix+".wav", "D#"));
        sounds.add(new Sound(path +"e.wav", "E"));
        sounds.add(new Sound(path +"f.wav", "F"));
        sounds.add(new Sound(path +"f"+prefix+".wav", "F#"));
        sounds.add(new Sound(path +"g.wav", "G"));
        sounds.add(new Sound(path +"g"+prefix+".wav", "G#"));
        sounds.add(new Sound(path +"a.wav", "A"));
        sounds.add(new Sound(path +"bb.wav", "BB"));
        sounds.add(new Sound(path +"b.wav", "B"));
        sounds.add(new Sound(path +"c2.wav", "C2"));
    }
}
