package pl.wszib.edu.pl.intochordsspringapp.model.dbo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "t_games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int gameId;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_name")
    private GameName gameName;

    public Game() {

    }

    public enum GameName {
        IntervalGame,
        ChordsGame
    }

    public Game(Integer gameId, GameName gameName) {
        this.gameId = gameId;
        this.gameName = gameName;
    }
}


