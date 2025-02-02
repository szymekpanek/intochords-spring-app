package pl.wszib.edu.pl.intochordsspringapp.dao.H2Initialize;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wszib.edu.pl.intochordsspringapp.dao.ClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.*;


@Component
@RequiredArgsConstructor
public class DataInitialization implements CommandLineRunner {
    private final UserDAO userDAO;
    private final GameDAO gameDAO;
    private final ClassDAO classDAO;

    @Override
    public void run(String... args) throws Exception {
        this.initializeData();
    }

    private void initializeData() {
        // Zapisujemy klasę i pobieramy zapisany obiekt
        TClass tClass = this.classDAO.save(new TClass(1, "Klasa 1", null, null));

//         Tworzymy użytkowników, przypisując im zapisany obiekt klasy
        User admin = new User(1, tClass, "Admin", "Admin", "admin", "admin", User.Role.ADMIN);
        User teacher = new User(2, tClass, "Nauczyciel", "Nauczyciel", "nauczyciel", "nauczyciel", User.Role.TEACHER);
        User user1 = new User(3, tClass, "Janusz", "Kowalski", "janusz", "janusz123", User.Role.USER);
        User user2 = new User(4, tClass, "Szymon", "Panek", "szymon", "szymon", User.Role.USER);

        this.userDAO.save(admin);
//        this.userRepository.save(teacher);
//        this.userRepository.save(user1);
//        this.userRepository.save(user2);

        // Tworzymy i zapisujemy gry
        Game game1 = new Game(1, Game.GameName.IntervalGame);
        Game game2 = new Game(2, Game.GameName.ChordsGame);

        this.gameDAO.save(game1);
        this.gameDAO.save(game2);
    }

}
