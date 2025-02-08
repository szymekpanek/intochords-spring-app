package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SessionController {

    @GetMapping("/session/teacher-id")
    public ResponseEntity<?> getTeacherId(HttpSession session) {
        Object teacherId = session.getAttribute("teacherId");

        if (teacherId == null) {
            return ResponseEntity.ok(Map.of("teacherId", -1)); // -1 oznacza brak przypisanej klasy
        }

        return ResponseEntity.ok(Map.of("teacherId", teacherId));
    }

}
