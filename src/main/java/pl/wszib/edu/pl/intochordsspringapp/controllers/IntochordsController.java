package pl.wszib.edu.pl.intochordsspringapp.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wszib.edu.pl.intochordsspringapp.dao.IntervalDB;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.model.Sound;
import pl.wszib.edu.pl.intochordsspringapp.services.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.dao.SoundDB;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.swing.*;
import java.util.List;
import java.util.Optional;


@Controller
public class IntochordsController {
    private final IntervalGameServices intervalGameServices;
    public IntochordsController (IntervalGameServices intervalGameServices){
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
    public String checkAnswer(HttpSession httpSession, @RequestParam String userAnswer, Model model, RedirectAttributes redirectAttributes){
        String randomInterval = (String) httpSession.getAttribute("randomInterval");

        if (intervalGameServices.checkAnswer(userAnswer, randomInterval)){
            redirectAttributes.addFlashAttribute("result", "Correct");
            System.out.println("Correct");
            httpSession.removeAttribute(randomInterval);
        }
        else {
            redirectAttributes.addFlashAttribute("result", "Not correct");
            System.out.println("Uncorrect");
        }

        httpSession.removeAttribute("result");

        return "redirect:interval-game";
    }


}