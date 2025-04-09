package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.Game;

import java.util.Optional;

public interface GameDAO extends JpaRepository<Game, Integer > {
    Game findByGameId(int gameId);
}
