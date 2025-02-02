package pl.wszib.edu.pl.intochordsspringapp.model.dbo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "t_game_stats")
public class GameStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_stats_id")
    private int gameStatsId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private Game game;

    @Column(name = "game_date")
    private LocalDateTime gameDate;

    @Column(name = "correct_answer")
    private int correctAnswer;

    @Column(name = "incorrect_answer")
    private int incorrectAnswer;

    public GameStats(Integer gameStatsId, User user, Game game, LocalDateTime gameDate, int correctAnswer, int incorrectAnswer) {
        this.gameStatsId = gameStatsId;
        this.user = user;
        this.game = game;
        this.gameDate = gameDate;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer = incorrectAnswer;
    }
}


