package pl.wszib.edu.pl.intochordsspringapp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wszib.edu.pl.intochordsspringapp.dao.IntervalDB;
import pl.wszib.edu.pl.intochordsspringapp.services.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.dao.SoundDB;

import javax.swing.*;


@Controller
public class IntochordsController {
    private final SoundDB soundDB;
    private final IntervalDB intervalDB;


    public IntochordsController (SoundDB soundDB, IntervalDB intervalDB){
        this.soundDB = soundDB;
        this.intervalDB = intervalDB;

    }

    @GetMapping("/")
    public String welcome(){
        return "welcome-page";
    }

    @GetMapping("/interval-game")
    public String intervals(Model model){
        model.addAttribute("sounds", soundDB.getSounds());
        model.addAttribute("intervals", intervalDB.getIntervalList());
        return "interval-game";
    }
}
