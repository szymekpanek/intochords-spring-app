package pl.wszib.edu.pl.intochordsspringapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
public class GameStatsDTO {
    private String gameName;
    private int correctAnswers;
    private int incorrectAnswers;
    private String gameDate;
}
