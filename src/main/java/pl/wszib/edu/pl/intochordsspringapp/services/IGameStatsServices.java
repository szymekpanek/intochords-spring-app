package pl.wszib.edu.pl.intochordsspringapp.services;

import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;

public interface IGameStatsServices {
    void saveGameStats(GameStats gameStats);
}
