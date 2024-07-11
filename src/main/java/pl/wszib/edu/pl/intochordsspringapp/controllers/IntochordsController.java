package pl.wszib.edu.pl.intochordsspringapp.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wszib.edu.pl.intochordsspringapp.dao.IntervalDB;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.model.Sound;
import pl.wszib.edu.pl.intochordsspringapp.services.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.dao.SoundDB;

import javax.swing.*;
import java.util.List;
import java.util.Optional;


@Controller
public class IntochordsController {
    private final SoundDB soundDB;
    private final IntervalDB intervalDB;
    private final IntervalGameServices intervalGameServices;


    public IntochordsController (SoundDB soundDB, IntervalDB intervalDB, IntervalGameServices intervalGameServices){
        this.soundDB = soundDB;
        this.intervalDB = intervalDB;
        this.intervalGameServices = intervalGameServices;
    }

    @GetMapping("/")
    public String welcome(){
        return "welcome-page";
    }

    @GetMapping("/interval-game")
    public String showIntervalGame(Model model, HttpSession httpSession) {
        Optional<Interval> randomIntervalOptional = intervalGameServices.getRandomInterval();
        if (randomIntervalOptional.isPresent()) {
            Interval randomInterval = randomIntervalOptional.get();
            model.addAttribute("intervals", intervalGameServices.getAllIntervals());
            model.addAttribute("randomInterval", randomInterval);
            model.addAttribute("sounds", intervalGameServices.findSoundsForRandomInterval(randomIntervalOptional));

            httpSession.setAttribute("randomInterval", randomInterval.getName());
        }
        return "interval-game";
    }

    @PostMapping("/check")
    public String checkAnswer(HttpSession httpSession, @RequestParam String userAnswer, Model model){
        String randomInterval = (String) httpSession.getAttribute("randomInterval");

        if (intervalGameServices.checkAnswer(userAnswer, randomInterval)){
            model.addAttribute("result", "Correct");

            System.out.println("Correct");

            httpSession.removeAttribute(randomInterval);
        }
        else {
            model.addAttribute("result", "Not correct");
            System.out.println("Uncorrect");
        }

        return "redirect:interval-game";
    }

}