package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.UserGameStatsService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-panel")
public class RestUserPanelController {

    private final UserGameStatsService userGameStatsService;

    @Autowired
    public RestUserPanelController(UserGameStatsService userGameStatsService) {
        this.userGameStatsService = userGameStatsService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserGameStats(HttpSession session) {
        User user = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (user == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        // Pobieramy statystyki gier użytkownika
        List<GameStats> userStats = userGameStatsService.getGameStatsByUser(user.getUserId());

        // Mapowanie wyników na bardziej czytelny format JSON
        List<Map<String, Object>> gameStatsList = userStats.stream().map(gs -> {
            Map<String, Object> gameStatsMap = new HashMap<>();
            gameStatsMap.put("gameStatsId", gs.getGameStatsId());
            gameStatsMap.put("gameId", gs.getGame().getGameId());
            gameStatsMap.put("gameName", gs.getGame().getGameName().name());
            gameStatsMap.put("gameDate", gs.getGameDate().toString());
            gameStatsMap.put("correctAnswer", gs.getCorrectAnswer());
            gameStatsMap.put("incorrectAnswer", gs.getIncorrectAnswer());
            return gameStatsMap;
        }).collect(Collectors.toList());

        // Tworzymy mapę z danymi użytkownika i jego statystykami gier
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getName());
        userData.put("surname", user.getSurname());
        userData.put("login", user.getLogin());

        // Dodanie nazwy klasy użytkownika
        if (user.getTClass() != null) {
            userData.put("className", user.getTClass().getClassName());
        } else {
            userData.put("className", "Brak przypisanej klasy");
        }

        response.put("user", userData);
        response.put("gameStats", gameStatsList);

        return ResponseEntity.ok(response);
    }

}
