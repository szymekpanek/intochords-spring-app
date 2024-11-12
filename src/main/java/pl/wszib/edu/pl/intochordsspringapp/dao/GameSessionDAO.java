package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wszib.edu.pl.intochordsspringapp.model.User;
import pl.wszib.edu.pl.intochordsspringapp.session.GameSession;

import java.util.List;

@Repository
public interface GameSessionDAO extends JpaRepository<GameSession, Long> {
    List<GameSession> findByUser(User user);
}
