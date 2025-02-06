package pl.wszib.edu.pl.intochordsspringapp.controllers.viewControllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserDAO userDAO;


    @GetMapping("admin-panel")
    public String showAdminPanel(Model model) {
        model.addAttribute("users", userDAO.findAll());
        return "user/admin-panel";
    }

    @PostMapping("admin-panel/delete/{id}")
    public String deleteUser(@PathVariable Integer id, HttpSession session, Model model) {
        Optional<User> userToDelete = userDAO.findById(id);

        if (userToDelete.isPresent()) {
            if (userToDelete.get().getRole() == User.Role.ADMIN) {
                model.addAttribute("errorMessage", "You cannot delete an ADMIN account.");
                return "redirect:/admin-panel";
            }
            userDAO.deleteById(id);
        }

        return "redirect:user/admin-panel";
    }

    @GetMapping("admin-panel/edit/{id}")
    public String editUserForm(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userDAO.findById(id).orElse(null));
        return "user/edit-user";
    }

    @PostMapping("admin-panel/update")
    public String updateUser(@ModelAttribute User user) {
        User existingUser = userDAO.findById(user.getUserId()).orElse(null);

        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            existingUser.setLogin(user.getLogin());
            existingUser.setRole(user.getRole());
            userDAO.save(existingUser);
        }
        return "redirect:/admin-panel";
    }
}


