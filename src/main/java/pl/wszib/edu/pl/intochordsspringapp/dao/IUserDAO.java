package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import pl.wszib.edu.pl.intochordsspringapp.model.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface IUserDAO {

    Optional<User> getById(Long id);
    Optional<User> getByLogin(String login);
    List<User> getAll();
    void save(User user);
    void remove(Long id);
    void update(User user);
}