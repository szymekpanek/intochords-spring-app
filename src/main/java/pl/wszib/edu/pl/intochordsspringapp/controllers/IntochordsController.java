package pl.wszib.edu.pl.intochordsspringapp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wszib.edu.pl.intochordsspringapp.dao.IntervalDB;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.model.Sound;
import pl.wszib.edu.pl.intochordsspringapp.services.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.dao.SoundDB;

import javax.swing.*;
import java.util.List;


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
    public String showIntervalGame(Model model) {
        model.addAttribute("intervals", intervalGameServices.getAllIntervals());
        model.addAttribute("sounds", intervalGameServices.findSoundsForRandomInterval());

        return "interval-game";
    }



}