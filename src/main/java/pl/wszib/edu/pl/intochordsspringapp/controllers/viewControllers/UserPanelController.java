package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.Optional;

@Controller
public class UserPanelController {

    private final UserDAO userDAO;

    @Autowired
    public UserPanelController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/user-panel")
    public String showUserPanel(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute(SessionConstants.USER_KEY);

        if (loggedInUser != null) {
            Optional<User> userOptional = userDAO.findById(loggedInUser.getUserId());

            if (userOptional.isPresent()) {
                model.addAttribute("user", userOptional.get());
                return "user-panel"; // Przekierowanie do widoku Thymeleaf: user-panel.html
            } else {
                return "redirect:/error"; // Obsługa błędu, jeśli użytkownik nie istnieje w bazie
            }
        } else {
            return "redirect:/login"; // Przekierowanie na stronę logowania, jeśli użytkownik nie jest zalogowany
        }
    }
}
