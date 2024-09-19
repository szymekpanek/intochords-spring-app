package pl.wszib.edu.pl.intochordsspringapp.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDB.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.memory.IdSequence;
import pl.wszib.edu.pl.intochordsspringapp.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInitialization implements CommandLineRunner {

    private final UserDAO userDAO;
    private final IdSequence idSequence;
    @Override
    public void run(String... args) throws Exception {
        this.userDAO.save(new User(null, "Janusz", "Kowalski",
                "janusz", DigestUtils.md5DigestAsHex("janusz123".getBytes()),User.Role.USER, 0,0));

        this.userDAO.save(new User(null, "Szymon", "Panek",
                "szymon", DigestUtils.md5DigestAsHex("szymon".getBytes()),User.Role.USER, 0,0));

        this.userDAO.save(new User(null, "admin", "admin",
                "admin", DigestUtils.md5DigestAsHex("admin".getBytes()),User.Role.ADMIN, 0,0));

        this.userDAO.save(new User(null, "nauczyciel", "nauczyciel",
                "nauczyciel",DigestUtils.md5DigestAsHex("nauczyciel".getBytes()) ,User.Role.TEACHER, 0,0));
    }
}
