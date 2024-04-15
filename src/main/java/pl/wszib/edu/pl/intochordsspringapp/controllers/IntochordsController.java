package pl.wszib.edu.pl.intochordsspringapp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wszib.edu.pl.intochordsspringapp.services.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.services.SoundDB;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Optional;


@Controller
public class IntochordsController {
    private final SoundDB soundDB;
    private IntervalGameServices intervalGameServices;
    public IntochordsController (SoundDB soundDB, IntervalGameServices intervalGameServices){
        this.soundDB = soundDB;
        this.intervalGameServices = intervalGameServices;
    }

    @GetMapping("/")
    public String welcome(){
        return "welcome-page";
    }

    @GetMapping("/interval-game")
    public String intervals(Model model, @RequestParam(required = false) String note) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        model.addAttribute("sounds", soundDB.getSoundMap());
        if (note != null) {
            intervalGameServices.playSound(note);
        }
        return "interval-game";
    }
}
