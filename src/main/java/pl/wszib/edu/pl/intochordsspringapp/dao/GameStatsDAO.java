package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.util.List;

public interface GameStatsDAO extends JpaRepository<GameStats, Integer> {
    List<GameStats> findByUser(User student);
    List<GameStats> findByUser_UserId(Integer userId);

}
