package pl.wszib.edu.pl.intochordsspringapp.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDB.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.User;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserDAO iuserDAO;


    @GetMapping("admin-panel")
    public String showAdminPanel(Model model) {
        model.addAttribute("users", iuserDAO.findAll());
        return "admin-panel";
    }

    @PostMapping("admin-panel/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session, Model model) {
        Optional<User> userToDelete = iuserDAO.findById(id);

        if (userToDelete.isPresent()) {
            if (userToDelete.get().getRole() == User.Role.ADMIN) {
                model.addAttribute("errorMessage", "You cannot delete an ADMIN account.");
                return "redirect:/admin-panel";
            }
            iuserDAO.deleteById(id);
        }

        return "redirect:/admin-panel";
    }

    @GetMapping("admin-panel/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", iuserDAO.findById(id).orElse(null));
        return "edit-user";
    }

    @PostMapping("admin-panel/update")
    public String updateUser(User user) {
        iuserDAO.save(user);
        return "redirect:/admin-panel";
    }
}
