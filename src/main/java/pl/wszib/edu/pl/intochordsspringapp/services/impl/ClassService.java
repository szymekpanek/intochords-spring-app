package pl.wszib.edu.pl.intochordsspringapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;

import java.util.List;

@Service
public class ClassGameStatsService {

    private final GameStatsDAO gameStatsDAO;

    @Autowired
    public ClassGameStatsService(GameStatsDAO gameStatsDAO) {
        this.gameStatsDAO = gameStatsDAO;
    }

    public List<GameStats> getGameStatsByUser(Integer userId) {
        return gameStatsDAO.findByUser_UserId(userId);
    }
}
