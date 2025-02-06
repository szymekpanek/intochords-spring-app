package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.util.List;

public interface GameStatsDAO extends JpaRepository<GameStats, Integer> {

//    List<GameStats> findByUser_UserId(int userId);
    List<GameStats> findByUser(User student);
    List<GameStats> findByUser_UserId(Integer userId);

//    List<GameStats> findByUserIn(List<User> users);

    @Query("SELECT gs FROM GameStats gs WHERE gs.user IN :users")
    List<GameStats> findByUserIn(List<User> users);

    @Query("SELECT gs.user, gs.game.gameName, DATE(gs.gameDate), SUM(gs.correctAnswer), SUM(gs.incorrectAnswer) " +
            "FROM GameStats gs WHERE gs.user IN :users " +
            "GROUP BY gs.user, gs.game.gameName, DATE(gs.gameDate) " +
            "ORDER BY DATE(gs.gameDate) DESC")
    List<Object[]> findSummarizedStatsByUser(List<User> users);

}
