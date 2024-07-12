//
//package pl.wszib.edu.pl.intochordsspringapp.memory;
//
//import org.springframework.stereotype.Component;
//import org.springframework.util.DigestUtils;
//import pl.wszib.edu.pl.intochordsspringapp.dao.IUserDAO;
//import pl.wszib.edu.pl.intochordsspringapp.exceptions.LoginAlreadyExistException;
//import pl.wszib.edu.pl.intochordsspringapp.model.User;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//@Component
//public class UserRepo implements IUserDAO {
//    private final List<User> users = new ArrayList<>();
//    private final IdSequence idSequence;
//
//    public UserRepo(IdSequence idSequence) {
//        this.idSequence = idSequence;
//        this.users.add(new User((long) this.idSequence.getId(), "Janusz", "Kowalski",
//                "janusz", DigestUtils.md5DigestAsHex("janusz123".getBytes()),1,10));
//        this.users.add(new User((long) this.idSequence.getId(), "Admin", "",
//                "admin", DigestUtils.md5DigestAsHex("root123".getBytes()),1,10));
//        this.users.add(new User((long) this.idSequence.getId(), "Szymon", "Panek",
//                "szymon", DigestUtils.md5DigestAsHex("szymon".getBytes()),40,20));
//    }
//
//    @Override
//    public Optional<User> getById(final Long id) {
//        return this.users.stream()
//                .filter(user -> user.getId().equals(id))
//                .findAny()
//                .map(this::copy);
//    }
//
//    @Override
//    public Optional<User> getByLogin(final String login) {
//        return this.users.stream()
//                .filter(user -> user.getLogin().equals(login))
//                .findAny()
//                .map(this::copy);
//    }
//
//    @Override
//    public List<User> getAll() {
//        return this.users.stream().map(this::copy).toList();
//    }
//
//    @Override
//    public void save(User user) {
//        user.setId((long) this.idSequence.getId());
//        this.getByLogin(user.getLogin()).ifPresent(u -> {
//            throw new LoginAlreadyExistException();
//        });
//        this.users.add(user);
//    }
//
//    @Override
//    public void remove(final Long id) {
//        this.users.removeIf(user -> user.getId().equals(id));
//    }
//
//    @Override
//    public void update(final User user) {
//        this.users.stream()
//                .filter(u -> u.getId().equals(user.getId()))
//                .findAny()
//                .ifPresent(u -> {
//                    u.setName(user.getName());
//                    u.setSurname(user.getSurname());
//                    u.setLogin(user.getLogin());
//                    u.setPassword(user.getPassword());
//                    u.setInterval_answer_inc(0);
//                    u.setInterval_answer_dec(0);
//                });
//    }
//
//    private User copy(User user) {
//        User u = new User();
//        u.setId(user.getId());
//        u.setName(user.getName());
//        u.setSurname(user.getSurname());
//        u.setLogin(user.getLogin());
//        u.setPassword(user.getPassword());
//        u.setInterval_answer_inc(0);
//        u.setInterval_answer_dec(0);
//        return u;
//    }
//}
