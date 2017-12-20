package tk.omi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.omi.model.Role;
import tk.omi.model.User;
import tk.omi.service.AppService;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private AppService appService;

    @GetMapping("/users")
    public String createUser(Model model, @RequestParam("userId") Optional<Long> userIdOptional) {
        User user = null;
        if (userIdOptional.isPresent()) {
            user = appService.getUser(userIdOptional.get());
        } else {
            user = new User();
        }
        model.addAttribute("user", user);
        return "users";
    }

    @PostMapping("/users")
    public String createU(@ModelAttribute("user") User user) {
        appService.save(user);
        return "redirect:/users";
    }

    @ModelAttribute("roles")
    public List<Role> populateRoles() {
        return appService.findAllRoles();
    }

    @ModelAttribute("users")
    public List<User> populateUsers() {
        List<User> users = appService.findAllUsers();
        users.forEach(user -> user.setNumberOfCustomers(appService.countCustomerByUser(user)));
        return users;
    }
}
