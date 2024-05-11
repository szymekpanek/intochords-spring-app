package pl.wszib.edu.pl.intochordsspringapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@NoArgsConstructor
@Component
public class Sound {
    private String path;
    private String name;

    public Sound(String path, String name) {
        this.path = path;
        this.name = name;
    }
}
