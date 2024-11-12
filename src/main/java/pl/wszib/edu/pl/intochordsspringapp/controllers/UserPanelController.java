package pl.wszib.edu.pl.intochordsspringapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameSessionDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.User;
import pl.wszib.edu.pl.intochordsspringapp.session.GameSession;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.List;

@Controller
public class UserPanelController {
    private final GameSessionDAO gameSessionDAO;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserPanelController(GameSessionDAO gameSessionDAO, ObjectMapper objectMapper) {
        this.gameSessionDAO = gameSessionDAO;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/user-panel")
    public String showUserPanel(Model model, HttpSession httpSession) {
        User loggedInUser = (User) httpSession.getAttribute(SessionConstants.USER_KEY);
        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser);
            List<GameSession> gameSessions = gameSessionDAO.findByUser(loggedInUser);
            try {
                String gameSessionsJson = objectMapper.writeValueAsString(gameSessions);
                model.addAttribute("gameSessionsJson", gameSessionsJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Jeśli użytkownik nie jest zalogowany, przekieruj do strony logowania lub pokaż komunikat o błędzie
            return "redirect:/login";
        }
        return "user-panel";
    }

}
