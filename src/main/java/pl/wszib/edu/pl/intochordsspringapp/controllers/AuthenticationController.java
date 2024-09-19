package pl.wszib.edu.pl.intochordsspringapp.controllers;

import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.dao.IUserDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDB.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.User;
import pl.wszib.edu.pl.intochordsspringapp.services.IAuthenticationService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;


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
        return "redirect:/";
    }


    @GetMapping("/sing-up")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "sing-up";
    }

    @PostMapping("/sing-up")
    public String createUser(@ModelAttribute User user, BindingResult result, Model model) {

//        if (userDAO.getAll(user.getLogin())) {
//            result.rejectValue("username", "error.user", "Username is already taken.");
//            return "create-user";
//        }

        if (user.getPassword().length() < 6) {
            result.rejectValue("password", "error.user", "Password must be at least 6 characters long.");
            return "create-user";
        }

        // Zapisanie nowego użytkownika
        userDAO.save(user);
        return "redirect:/";
    }

}