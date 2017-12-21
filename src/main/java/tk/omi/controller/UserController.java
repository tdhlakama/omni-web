package tk.omi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.omi.model.Role;
import tk.omi.model.User;
import tk.omi.service.AppService;

import javax.validation.Valid;
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
    public String createU(Model model, @ModelAttribute("user") User user, BindingResult bindingResult) {

        User userExists = appService.findByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.username",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "users";
        } else {
            appService.save(user);
            return "redirect:/users";
        }
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
