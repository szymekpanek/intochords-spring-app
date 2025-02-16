package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.UserGameStatsService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.Map;

@RestController
@RequestMapping("/api/user-panel")
public class RestUserPanelController {

    @Autowired
    private UserGameStatsService userGameStatsService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserGameStats(HttpSession session) {
        User user = (User) session.getAttribute(SessionConstants.USER_KEY);
        return userGameStatsService.getUserGameStats(user);
    }

}
