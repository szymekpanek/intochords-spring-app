package pl.wszib.edu.pl.intochordsspringapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "tuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Role role;
    private Integer interval_answer_inc;
    private Integer interval_answer_dec;

    public enum Role {
        ADMIN,
        USER,
        TEACHER
    }
}

