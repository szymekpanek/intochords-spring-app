package pl.wszib.edu.pl.intochordsspringapp.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.services.IGameStatsServices;


@Service
public class GameStatsService implements IGameStatsServices {

    @Autowired
    private GameStatsDAO gameStatsDAO;

    public void saveGameStats(GameStats gameStats) {
        gameStatsDAO.save(gameStats);
    }
}
