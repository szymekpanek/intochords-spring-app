package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.GameServices;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.Map;

@RestController
@RequestMapping("/api/interval-game")
public class RestIntervalGameController {
    private final GameServices intervalGameServices;

    public RestIntervalGameController(GameServices intervalGameServices) {
        this.intervalGameServices = intervalGameServices;
    }


    @GetMapping("/get-random")
    public ResponseEntity<Map<String, String>> getRandomInterval() {
        Map<String, String> response = intervalGameServices.getGameData();
        return response.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(response);
    }


    @PostMapping("/save-game-results")
    public ResponseEntity<String> saveGameResults(HttpSession session,
                                                  @RequestParam int correct,
                                                  @RequestParam int incorrect) {
        User user = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        intervalGameServices.saveGameResults(user, correct, incorrect, 1);
        return ResponseEntity.ok("Results saved.");
    }
}
