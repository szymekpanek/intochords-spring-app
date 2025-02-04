package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return "interval-game";
    }
}
