package pl.wszib.edu.pl.intochordsspringapp.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;

import java.util.Optional;
@Controller
public class ChordsGameController {

    @GetMapping("/chords-game")
    public String showIntervalGame() {
        return "chords-game";
    }
}
