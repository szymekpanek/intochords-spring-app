package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wszib.edu.pl.intochordsspringapp.dao.ClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TClass;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ClassController {

    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @Autowired
    private ClassDAO classDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GameStatsDAO gameStatsDAO;

    private final String classesPath = "classes/";

    /**
     * Metoda pomocnicza sprawdzająca, czy użytkownik jest nauczycielem.
     */
    private User getLoggedTeacher(HttpSession session) {
        User teacher = (User) session.getAttribute(SessionConstants.USER_KEY);
        if (teacher == null || teacher.getRole() != User.Role.TEACHER) {
            return null;
        }
        return teacher;
    }

    @GetMapping("/class-panel/{classId}")
    public String showClassPanel(@PathVariable Integer classId, HttpSession session, Model model) {
        User teacher = getLoggedTeacher(session);
        if (teacher == null) {
            return "redirect:/";
        }

        Optional<TClass> existingClass = classDAO.findById(classId);
        if (existingClass.isEmpty()) {
            return "redirect:/class-panel/create";
        }

        model.addAttribute("classId", classId); // ✅ Upewniamy się, że `classId` jest w modelu
        model.addAttribute("className", existingClass.get().getClassName());

        return "classes/class-panel";
    }



    @GetMapping("/class-panel/create")
    public String showCreateClassPage(HttpSession session) {
        User teacher = getLoggedTeacher(session);
        if (teacher == null) {
            return "redirect:/";
        }
        return classesPath + "create-class";
    }

    @GetMapping("/class-panel/add-students")
    public String showAddStudentsPage(@RequestParam("classId") Integer classId, Model model, HttpSession session) {
        User teacher = getLoggedTeacher(session);
        if (teacher == null) {
            return "redirect:/";
        }

        Optional<TClass> existingClass = classDAO.findById(classId);
        if (existingClass.isEmpty()) {
            logger.warn("🔴 Błąd: Klasa o ID {} nie istnieje.", classId);
            return "redirect:/class-panel";
        }

        TClass tClass = existingClass.get();
        logger.info("✅ Klasa znaleziona: {}. Przekierowanie do add-students.", tClass.getClassName());
        model.addAttribute("classId", classId);
        return "classes/add-students";
    }

    @GetMapping("/class-panel/edit-class")
    public String showEditClassPage(@RequestParam("classId") Integer classId, Model model, HttpSession session) {
        User teacher = getLoggedTeacher(session);
        if (teacher == null) {
            return "redirect:/";
        }

        Optional<TClass> existingClass = classDAO.findById(classId);
        if (existingClass.isEmpty()) {
            return "redirect:/class-panel";
        }

        TClass tClass = existingClass.get();
        List<User> students = userDAO.findUsersByClassId(classId);

        model.addAttribute("classId", classId);
        model.addAttribute("className", tClass.getClassName());
        model.addAttribute("students", students);

        return "classes/edit-class";
    }

}
