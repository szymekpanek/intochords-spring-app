package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChordsGameController {

    @GetMapping("/chords-game")
    public String showIntervalGame() {
        return "chords-game";
    }
}
