package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;

import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.IAuthenticationService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.Optional;


@Controller
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @Autowired
    HttpSession httpSession;

    @Autowired
    UserDAO userDAO;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("loginInfo", this.authenticationService.getLoginInfo());
        return "login";
    }

    @PostMapping("/login")
    public String login2(@RequestParam String login, @RequestParam String password) {
        this.authenticationService.login(login, password);
        if(this.httpSession.getAttribute(SessionConstants.USER_KEY) != null) {
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/"; // Przekierowanie do strony głównej po wylogowaniu
    }

    @GetMapping("/sing-up")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "sing-up";
    }

    @PostMapping("/sing-up")
    public String createUser(@ModelAttribute User user, BindingResult result) {
        if (userDAO.findByLogin(user.getLogin()).isPresent()) {
            result.rejectValue("login", "error.user", "Login is already taken.");
            return "sing-up";
        }

        if (user.getPassword().length() < 6) {
            result.rejectValue("password", "error.user", "Password must be at least 6 characters long.");
            return "sing-up";
        }

        // Hashowanie hasła przed zapisaniem
        String hashedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(hashedPassword);

        userDAO.save(user); // Zapisanie nowego użytkownika do bazy
        return "redirect:/"; // Po zapisaniu użytkownika przekierowanie na stronę główną
    }
}
