package pl.wszib.edu.pl.intochordsspringapp.services.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDB.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.User;
import pl.wszib.edu.pl.intochordsspringapp.services.IAuthenticationService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserDAO userDAO;
    private final HttpSession httpSession;

    @Override
    public void login(String login, String password) {
        Optional<User> user = this.userDAO.findByLogin(login);
        if (user.isPresent() &&
                DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.get().getPassword())) {
            System.out.println("Login successful for user: " + user.get().getLogin());
            System.out.println("ID user: " + user.get().getUser_id());
            httpSession.setAttribute(SessionConstants.USER_KEY, user.get());
            httpSession.setAttribute(SessionConstants.CART_KEY, new HashSet<>());
            return;
        }
        this.httpSession.setAttribute("loginInfo", "Invalid credentials");
    }

    @Override
    public void logout() {
        this.httpSession.removeAttribute(SessionConstants.USER_KEY);
        this.httpSession.removeAttribute(SessionConstants.CART_KEY);
    }

    @Override
    public String getLoginInfo() {
        String temp = (String) this.httpSession.getAttribute("loginInfo");
        this.httpSession.removeAttribute("loginInfo");
        return temp;
    }
}