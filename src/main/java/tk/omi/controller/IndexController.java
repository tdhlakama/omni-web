package tk.omi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.omi.model.Customer;
import tk.omi.model.Role;
import tk.omi.model.User;
import tk.omi.service.AppService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {

    @Autowired
    private AppService appService;

    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping({"/", "/list"})
    public String list(Model model, @RequestParam(value = "userId") Optional<Long> userIdOptional) {
        List<Customer> customers = new ArrayList<>();
        if (userIdOptional.isPresent()) {
            User user = appService.getUser(userIdOptional.get());
            customers.addAll(appService.findByUser(user));
            model.addAttribute("agent", user);
        } else {
            customers.addAll(appService.findAllCustomers());
        }
        model.addAttribute("customers", customers);
        return "index";
    }

    @ModelAttribute("roles")
    public List<Role> populateRoles() {
        return appService.findAllRoles();
    }

    @ModelAttribute("users")
    public List<User> populateUsers() {
        return appService.findAllUsers();
    }

}
