package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

@Controller
public class FilesController {

    @GetMapping("/files")
    public String filesPage(@RequestParam(name = "classId", required = false) Integer classId,
                            HttpSession session, Model model) {
        User user = (User) session.getAttribute(SessionConstants.USER_KEY);

        if (user == null) {
            return "redirect:/login"; // ✅ Jeśli użytkownik nie jest zalogowany, przekierowanie do logowania
        }

        // Jeśli `classId` nie jest przekazany w URL → przekierowanie na stronę główną
        if (classId == null) {
            return "redirect:/";
        }

        Integer sessionClassId = (Integer) session.getAttribute("classId");

        // Jeśli użytkownik nie należy do tej klasy, przekierowanie na stronę główną
        if (sessionClassId == null || !sessionClassId.equals(classId)) {
            return "redirect:/";
        }

        model.addAttribute("classId", classId);
        return "user/files"; // ✅ Wyświetlamy stronę `files.html`
    }
}
