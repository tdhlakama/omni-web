package tk.omi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tk.omi.model.Customer;
import tk.omi.model.CustomerDocument;
import tk.omi.model.Role;
import tk.omi.model.User;
import tk.omi.service.AppService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @RequestMapping("/inform")
    public String about() {
        return "inform";
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


    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public void showImagePhote(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {

        Customer customer = appService.getCustomer(id);
        Optional<CustomerDocument> documentOptional = appService
                .getCustomerDocument(customer, CustomerDocument.IMAGE);

        if (documentOptional.isPresent()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(documentOptional.get().getDocument());

            response.getOutputStream().close();
        }
    }

    @RequestMapping(value = "/copyId", method = RequestMethod.GET)
    public void showImageID(@RequestParam("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = appService.getCustomer(id);
        Optional<CustomerDocument> documentOptional = appService
                .getCustomerDocument(customer, CustomerDocument.COPY_ID);

        if (documentOptional.isPresent()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(documentOptional.get().getDocument());

            response.getOutputStream().close();
        }
    }

    @RequestMapping(value = "/signature", method = RequestMethod.GET)
    public void showImageSignature(@RequestParam("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = appService.getCustomer(id);
        Optional<CustomerDocument> documentOptional = appService
                .getCustomerDocument(customer, CustomerDocument.SIGNATURE);

        if (documentOptional.isPresent()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(documentOptional.get().getDocument());

            response.getOutputStream().close();
        }
    }

    @RequestMapping(value = "/proof", method = RequestMethod.GET)
    public void showProofImage(@RequestParam("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = appService.getCustomer(id);
        Optional<CustomerDocument> documentOptional = appService
                .getCustomerDocument(customer, CustomerDocument.PROOF_OF_RESIDENCE);

        if (documentOptional.isPresent()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(documentOptional.get().getDocument());

            response.getOutputStream().close();
        }
    }

    @RequestMapping("/customer")
    public String list(Model model, @RequestParam(value = "id") Long id) {
        List<Customer> customers = new ArrayList<>();
        if (id != null) {
            Customer customer = appService.getCustomer(id);
            model.addAttribute("customer", customer);
        }
        return "customer";
    }

    @RequestMapping("/invalidSession")
    public String invalidSession() {
        return "invalidSession";
    }

    @RequestMapping("/expiredSession")
    public String expiredSession() {
        return "expiredSession";
    }

}
