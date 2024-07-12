package pl.wszib.edu.pl.intochordsspringapp.memory;

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
    private String path = "/mp3_files/";
    private String prefix = "is";
    public SoundDB(){
        sounds.add(new Sound(path+ "c.mp3", "C"));
        sounds.add(new Sound(path +"c"+prefix+".mp3", "C#"));
        sounds.add(new Sound(path +"d.mp3", "D"));
        sounds.add(new Sound(path +"d"+prefix+".mp3", "D#"));
        sounds.add(new Sound(path +"e.mp3", "E"));
        sounds.add(new Sound(path +"f.mp3", "F"));
        sounds.add(new Sound(path +"f"+prefix+".mp3", "F#"));
        sounds.add(new Sound(path +"g.mp3", "G"));
        sounds.add(new Sound(path +"g"+prefix+".mp3", "G#"));
        sounds.add(new Sound(path +"a.mp3", "A"));
        sounds.add(new Sound(path +"bb.mp3", "BB"));
        sounds.add(new Sound(path +"b.mp3", "B"));
        sounds.add(new Sound(path +"c2.mp3", "C2"));
        sounds.add(new Sound(path +"cis2.mp3", "C#2"));
        sounds.add(new Sound(path +"d2.mp3", "D2"));
        sounds.add(new Sound(path +"dis2.mp3", "D#2"));
        sounds.add(new Sound(path +"e2.mp3", "E2"));
        sounds.add(new Sound(path +"f2.mp3", "F2"));
        sounds.add(new Sound(path +"fis2.mp3", "F#2"));
        sounds.add(new Sound(path +"g2.mp3", "G2"));
        sounds.add(new Sound(path +"gis2.mp3", "G#2"));
        sounds.add(new Sound(path +"a2.mp3", "A2"));
        sounds.add(new Sound(path +"bb2.mp3", "BB2"));
        sounds.add(new Sound(path +"b2.mp3", "B2"));
    }
}
