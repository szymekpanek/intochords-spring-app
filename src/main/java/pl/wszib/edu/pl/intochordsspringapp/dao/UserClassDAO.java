package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.UserClass;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.UserClassId;

import java.util.Optional;

public interface UserClassDAO extends JpaRepository<UserClass, UserClassId> {

    Optional<UserClass> findByUser(User student);
}
