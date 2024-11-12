package pl.wszib.edu.pl.intochordsspringapp.session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wszib.edu.pl.intochordsspringapp.model.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String gameType; // np. "interval"
    private String gameName; // np. nazwa gry
    private LocalDateTime timestamp;
    private boolean isCorrect;

    public GameSession(User user, String gameType, String gameName, LocalDateTime timestamp, boolean isCorrect) {
        this.user = user;
        this.gameType = gameType;
        this.gameName = gameName;
        this.timestamp = timestamp;
        this.isCorrect = isCorrect;
    }



}

