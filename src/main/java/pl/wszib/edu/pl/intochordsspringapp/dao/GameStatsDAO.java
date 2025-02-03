package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;

import java.util.List;

public interface GameStatsDAO extends JpaRepository<GameStats, Integer> {

    List<GameStats> findByUser_UserId(int userId);
}
