package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.GameStatsService;

@RestController
@RequestMapping("/submit-game-stats")
public class GameStatsController {

    @Autowired
    private GameStatsService gameStatsService;

    @PostMapping
    public void submitGameStats(@RequestBody GameStats gameStats) {
        gameStatsService.saveGameStats(gameStats);
    }
}

