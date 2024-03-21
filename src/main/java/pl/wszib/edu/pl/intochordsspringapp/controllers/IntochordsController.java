package pl.wszib.edu.pl.intochordsspringapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IntochordsController {
    @GetMapping("/")
    public String welcome(){
        return "welcome-page";
    }

    @GetMapping("/about")
    public String intervals(){
        return "interval-game";
    }
}
