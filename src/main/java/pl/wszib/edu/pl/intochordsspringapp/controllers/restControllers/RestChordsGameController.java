package pl.wszib.edu.pl.intochordsspringapp.controllers.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.Game;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/chords-game")
public class RestChordsGameController {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private GameStatsDAO gameStatsDAO;

    @PostMapping("/save-game-results")
    public ResponseEntity<String> saveGameResults(HttpSession session, @RequestParam int correct, @RequestParam int incorrect) {
        User user = (User) session.getAttribute(SessionConstants.USER_KEY);

        if (user == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }

        Game game = gameDAO.findByGameId(2); // ID gry w bazie to 2

        GameStats stats = new GameStats();
        stats.setUser(user);
        stats.setGame(game);
        stats.setGameDate(LocalDateTime.now());

        // Zapisujemy tylko te wartości, które mają sens
        if (correct > 0) {
            stats.setCorrectAnswer(correct);
            stats.setIncorrectAnswer(0);
        } else {
            stats.setCorrectAnswer(0);
            stats.setIncorrectAnswer(incorrect);
        }

        gameStatsDAO.save(stats);
        return ResponseEntity.ok("Game results saved successfully");
    }

}
