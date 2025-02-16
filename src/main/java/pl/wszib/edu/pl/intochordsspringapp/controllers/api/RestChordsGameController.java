package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.GameServices;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/chords-game")
public class RestChordsGameController {

    @Autowired
    private GameServices chordsGameService;

    @PostMapping("/save-game-results")
    public ResponseEntity<String> saveGameResults(HttpSession session, @RequestParam int correct, @RequestParam int incorrect) {
        User user = (User) session.getAttribute(SessionConstants.USER_KEY);
        return chordsGameService.saveGameResults(user, correct, incorrect, 2);
    }

}
