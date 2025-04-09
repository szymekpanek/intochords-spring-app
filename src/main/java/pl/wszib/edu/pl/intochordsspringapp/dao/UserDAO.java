package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TClass;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.UserClass;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    List<User> findByUserIdIn(List<Integer> studentIds);

    List<User> findByRole(User.Role role);

    @Query("SELECT uc.user FROM UserClass uc WHERE uc.TClass.classId = :classId")
    List<User> findUsersByClassId(int classId);
}
