package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GameStatsDAO extends CrudRepository<GameStats, Integer> {

    @Query("SELECT new map(FUNCTION('DATE', gs.gameDate) as gameDate, " +
            "COALESCE(SUM(gs.correctAnswer), 0) as correct, " +
            "COALESCE(SUM(gs.incorrectAnswer), 0) as incorrect) " +
            "FROM GameStats gs " +
            "WHERE gs.user.userId = :userId " +
            "AND gs.game.gameId = :gameId " +
            "AND gs.gameDate >= :startDate " +
            "GROUP BY FUNCTION('DATE', gs.gameDate) " +
            "ORDER BY FUNCTION('DATE', gs.gameDate) ASC")
    List<Map<String, Object>> findUserGameStats(@Param("userId") int userId,
                                                @Param("gameId") int gameId,
                                                @Param("startDate") LocalDate startDate);
}
