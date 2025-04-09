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
    public String showUserPanel(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (loggedInUser != null) {
            Optional<User> userOptional = userDAO.findById(loggedInUser.getUserId());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("user", user);

                // Dodajemy informację o klasie
                if (user.getTClass() != null) {
                    model.addAttribute("userClass", user.getTClass().getClassName());
                } else {
                    model.addAttribute("userClass", "No class assigned");
                }

                return "user/user-panel";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:user/login";
        }
    }

}
