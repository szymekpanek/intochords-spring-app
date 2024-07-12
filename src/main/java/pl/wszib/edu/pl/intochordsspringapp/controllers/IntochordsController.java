package pl.wszib.edu.pl.intochordsspringapp.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wszib.edu.pl.intochordsspringapp.dao.IUserDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDB.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.model.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.IntervalGameServices;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;


import java.util.List;
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
    public String welcome(){
        return "welcome-page";
    }



    @GetMapping("/user-panel")
    public String showUserPanel(Model model, HttpSession session) {
        // Retrieve logged-in user from session
        User loggedInUser = (User) session.getAttribute(SessionConstants.USER_KEY);
        // Check if user is logged in
        if (loggedInUser != null) {
            // Retrieve user from the database using the logged-in user's ID
            Optional<User> userOptional = userDAO.findById(loggedInUser.getId());
            System.out.println("W user panel id: " + loggedInUser.getId() + "wygrane w panelu: " + loggedInUser.getInterval_answer_inc());
            System.out.println("W user panel id: " + loggedInUser.getId() + "przegrane w panelu: " + loggedInUser.getInterval_answer_dec());


            // Check if user exists in the database
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("user", user); // Add user to model
                return "user-panel"; // Return the user-panel template
            } else {
                // Handle case where user ID doesn't exist (though it should not happen if session user is valid)
                return "redirect:/error"; // Redirect to error page
            }
        } else {
            // Redirect to login if user is not logged in
            return "redirect:/login";
        }
    }
}