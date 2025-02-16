package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.dao.ClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.*;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.ClassService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import jakarta.servlet.http.HttpSession;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/class")
public class RestClassController {

    @Autowired
    private ClassService classService;

    @PostMapping("/create")
    public ResponseEntity<String> createClass(@RequestParam String className, HttpSession session) {
        User teacher = (User) session.getAttribute(SessionConstants.USER_KEY);

        String response = classService.createClass(className, teacher);

        if (response.equals("Only teachers can create classes.")) {
            return ResponseEntity.status(403).body(response);
        } else if (response.equals("Class name already taken.")) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-available-students")
    public ResponseEntity<List<Map<String, Object>>> getAvailableStudents() {
        return ResponseEntity.ok(classService.getAvailableStudents());
    }

    @PostMapping("/add-students")
    public ResponseEntity<?> addStudentsToClass(@RequestBody Map<String, Object> payload) {
        return classService.addStudentsToClass(payload);
    }

    @GetMapping("/get-teacher-class")
    public ResponseEntity<Map<String, Object>> getTeacherClass(HttpSession session) {
        User teacher = (User) session.getAttribute(SessionConstants.USER_KEY);
        return classService.getTeacherClass(teacher);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<Map<String, Object>> getClassGameStats(@PathVariable Integer classId) {
        return classService.getClassGameStats(classId);
    }


    @PostMapping("/edit-name")
    public ResponseEntity<?> editClassName(@RequestBody Map<String, Object> payload) {
        return classService.editClassName(payload);
    }


    @PostMapping("/remove-student")
    public ResponseEntity<?> removeStudentFromClass(@RequestBody Map<String, Object> payload) {
        return classService.removeStudentFromClass(payload);
    }


}