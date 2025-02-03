package pl.wszib.edu.pl.intochordsspringapp.controllers.restControllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.UserStatsService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-stats")
public class UserStatsController {

    private final UserStatsService userStatsService;

    @Autowired
    public UserStatsController(UserStatsService userStatsService) {
        this.userStatsService = userStatsService;
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<Map<String, Object>>> getUserStats(
            HttpSession session,
            @PathVariable("gameId") int gameId,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        User user = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (user == null) {
            return ResponseEntity.status(401).body(Collections.emptyList());
        }

        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1);
        }

        List<Map<String, Object>> stats = userStatsService.getUserStatsForGame(user.getUserId(), gameId, startDate);

        return ResponseEntity.ok(stats == null ? Collections.emptyList() : stats);
    }
}
