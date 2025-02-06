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

@Controller
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserDAO userDAO;

    private String path = "user/";

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginInfo", this.authenticationService.getLoginInfo());
        return path + "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        this.authenticationService.login(login, password);
        if (this.httpSession.getAttribute(SessionConstants.USER_KEY) != null) {
            return "redirect:/";
        }
        return "redirect:" + path + "login";
    }

    @GetMapping("/logout")
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/"; // Przekierowanie do strony głównej po wylogowaniu
    }

    @GetMapping("/sign-up")  // Poprawiona ścieżka
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return path + "sign-up";  // Poprawiona ścieżka widoku
    }

    @PostMapping("/sign-up")  // Poprawiona ścieżka
    public String createUser(@ModelAttribute User user, BindingResult result, Model model) {
        // Sprawdzenie, czy login jest już zajęty
        if (userDAO.findByLogin(user.getLogin()).isPresent()) {
            result.rejectValue("login", "error.user", "Login is already taken.");
            model.addAttribute("errorMessage", "Login is already taken.");
            return path + "sign-up";
        }

        // Sprawdzenie długości hasła
        if (user.getPassword().length() < 6) {
            result.rejectValue("password", "error.user", "Password must be at least 6 characters long.");
            model.addAttribute("errorMessage", "Password must be at least 6 characters long.");
            return path + "sign-up";
        }

        // Sprawdzenie imienia i nazwiska
        if (user.getName().length() < 2 || !user.getName().matches("[A-Za-z]+")) {
            model.addAttribute("errorMessage", "Invalid name. Must be at least 2 letters.");
            return path + "sign-up";
        }
        if (user.getSurname().length() < 2 || !user.getSurname().matches("[A-Za-z]+")) {
            model.addAttribute("errorMessage", "Invalid surname. Must be at least 2 letters.");
            return path + "sign-up";
        }

        // Hashowanie hasła przed zapisaniem
        String hashedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(hashedPassword);

        // Zapisanie użytkownika
        userDAO.save(user);

        // Automatyczne logowanie po rejestracji
        this.httpSession.setAttribute(SessionConstants.USER_KEY, user);

        return "redirect:/"; // Przekierowanie na stronę główną
    }
}
