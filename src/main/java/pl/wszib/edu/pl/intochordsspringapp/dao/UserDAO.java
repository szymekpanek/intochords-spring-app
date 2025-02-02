package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.repository.CrudRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Integer> {
    User findByUserId(Integer user_id);
    Optional<User> findByLogin(String login);
}
