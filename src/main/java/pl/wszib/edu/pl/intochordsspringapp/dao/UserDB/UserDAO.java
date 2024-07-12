package pl.wszib.edu.pl.intochordsspringapp.dao.UserDB;

import org.springframework.data.repository.CrudRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.User;

import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
