package pl.wszib.edu.pl.intochordsspringapp.model.dbo;

import jakarta.persistence.*;
import lombok.Getter;
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

    @Setter
    @Column(name = "correct_answer")
    private int correctAnswer;

    @Setter
    @Column(name = "incorrect_answer")
    private int incorrectAnswer;



    public GameStats() {}

    public GameStats(User user, Game game, LocalDateTime gameDate, int correctAnswer, int incorrectAnswer) {
        this.user = user;
        this.game = game;
        this.gameDate = gameDate;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer = incorrectAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setIncorrectAnswer(int incorrectAnswer) {
        this.incorrectAnswer = incorrectAnswer;
    }
}


