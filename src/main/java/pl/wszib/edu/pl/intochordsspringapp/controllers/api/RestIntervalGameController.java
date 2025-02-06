package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.Map;

@RestController
@RequestMapping("/api/interval-game")
public class RestIntervalGameController {
    private final IntervalGameServices intervalGameServices;

    @Autowired
    public RestIntervalGameController(IntervalGameServices intervalGameServices) {
        this.intervalGameServices = intervalGameServices;
    }

    /**
     * GET - Zwraca losowe interwały i dźwięki do gry (dla JavaScriptu).
     */
    @GetMapping("/get-random")
    public ResponseEntity<Map<String, String>> getRandomInterval() {
        Map<String, String> response = intervalGameServices.getGameData();
        return response.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(response);
    }

    /**
     * POST - Zapisuje wyniki gry użytkownika.
     */
    @PostMapping("/save-game-results")
    public ResponseEntity<String> saveGameResults(HttpSession session,
                                                  @RequestParam int correct,
                                                  @RequestParam int incorrect) {
        User user = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        intervalGameServices.saveGameResults(user, correct, incorrect);
        return ResponseEntity.ok("Results saved.");
    }
}
