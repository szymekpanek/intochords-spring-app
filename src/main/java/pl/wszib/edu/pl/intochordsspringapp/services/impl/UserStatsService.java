package pl.wszib.edu.pl.intochordsspringapp.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class UserStatsService {

    private final GameStatsDAO gameStatsDAO;

    @Autowired
    public UserStatsService(GameStatsDAO gameStatsDAO) {
        this.gameStatsDAO = gameStatsDAO;
    }

    public List<Map<String, Object>> getUserStatsForGame(int userId, int gameId, LocalDate startDate) {
        return gameStatsDAO.findUserGameStats(userId, gameId, startDate);
    }
}

