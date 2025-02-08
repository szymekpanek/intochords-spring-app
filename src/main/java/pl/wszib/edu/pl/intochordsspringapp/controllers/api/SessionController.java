package pl.wszib.edu.pl.intochordsspringapp.controllers.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    /**
     * Pobiera ID zalogowanego użytkownika.
     * @param session Sesja HTTP użytkownika
     * @return ID użytkownika lub -1 jeśli nie jest zalogowany
     */
    @GetMapping("/user-id")
    public ResponseEntity<?> getUserId(HttpSession session) {
        User user = (User) session.getAttribute(SessionConstants.USER_KEY);

        if (user == null) {
            return ResponseEntity.ok(Map.of("userId", -1)); // -1 oznacza brak zalogowanego użytkownika
        }

        return ResponseEntity.ok(Map.of("userId", user.getUserId()));
    }

    /**
     * Pobiera ID klasy, do której należy użytkownik.
     * @param session Sesja HTTP użytkownika
     * @return ID klasy lub -1 jeśli użytkownik nie jest przypisany do żadnej klasy
     */
    @GetMapping("/class-id")
    public ResponseEntity<?> getUserClassId(HttpSession session) {
        Object classId = session.getAttribute("classId");

        if (classId == null) {
            return ResponseEntity.ok(Map.of("classId", -1)); // -1 oznacza brak klasy
        }

        return ResponseEntity.ok(Map.of("classId", classId));
    }
}
