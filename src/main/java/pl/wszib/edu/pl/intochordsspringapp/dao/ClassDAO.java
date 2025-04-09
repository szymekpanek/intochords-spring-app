package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TClass;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.util.Optional;

public interface ClassDAO extends JpaRepository<TClass, Integer> {

    Optional<TClass> findByClassName(String className);

    Optional<TClass> findByCreator(User teacher);

}

