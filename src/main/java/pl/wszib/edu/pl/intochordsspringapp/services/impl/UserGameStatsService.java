package pl.wszib.edu.pl.intochordsspringapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserGameStatsService {

    private final GameStatsDAO gameStatsDAO;

    @Autowired
    public UserGameStatsService(GameStatsDAO gameStatsDAO) {
        this.gameStatsDAO = gameStatsDAO;
    }

    public ResponseEntity<Map<String, Object>> getUserGameStats(User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Pobieramy statystyki gier użytkownika
        List<GameStats> userStats = gameStatsDAO.findByUser(user);

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
