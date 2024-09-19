package pl.wszib.edu.pl.intochordsspringapp.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDB.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;


import java.util.Optional;


@Controller
public class IntochordsController {
    private final IntervalGameServices intervalGameServices;
    private final UserDAO userDAO;

    public IntochordsController (IntervalGameServices intervalGameServices, UserDAO userDAO){
        this.intervalGameServices = intervalGameServices;
        this.userDAO = userDAO;
    }

    @GetMapping("/")
    public String welcome(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (loggedInUser != null) {
            Optional<User> userOptional = userDAO.findById(loggedInUser.getId());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("username", user.getName());
                model.addAttribute("user", user);

            }
        }
        return "welcome-page";
    }

    @GetMapping("/user-panel")
    public String showUserPanel(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (loggedInUser != null) {
            Optional<User> userOptional = userDAO.findById(loggedInUser.getId());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("user", user);
                return "user-panel";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/login";
        }
    }
}