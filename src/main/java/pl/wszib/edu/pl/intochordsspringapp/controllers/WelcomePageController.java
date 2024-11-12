package pl.wszib.edu.pl.intochordsspringapp.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameSessionDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDB.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.session.GameSession;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;


import java.util.List;
import java.util.Optional;


@Controller
public class WelcomePageController {
    private final IntervalGameServices intervalGameServices;
    private final UserDAO userDAO;

    private GameSessionDAO gameSessionDAO;

    public WelcomePageController(IntervalGameServices intervalGameServices, UserDAO userDAO){
        this.intervalGameServices = intervalGameServices;
        this.userDAO = userDAO;
    }

    @GetMapping("/")
    public String welcome(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (loggedInUser != null) {
            Optional<User> userOptional = userDAO.findById(loggedInUser.getUser_id());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("username", user.getName());
                model.addAttribute("user", user);

            }
        }
        return "welcome-page";
    }
}