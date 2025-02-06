package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.IntervalGameServices;

import java.util.List;

@Controller
@RequestMapping("/interval-game")
public class IntervalController {
    private final IntervalGameServices intervalGameServices;

    @Autowired
    public IntervalController(IntervalGameServices intervalGameServices) {
        this.intervalGameServices = intervalGameServices;
    }

    @GetMapping
    public String showIntervalGame(Model model) {
        List<Interval> intervals = intervalGameServices.getAllIntervals();
        model.addAttribute("intervals", intervals);
        return "games/interval-game";
    }
}
