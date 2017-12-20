package tk.omi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tk.omi.model.Customer;
import tk.omi.model.CustomerDocument;
import tk.omi.model.User;
import tk.omi.service.AppService;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@MultipartConfig(fileSizeThreshold = 20971520)
public class OmniRestController {

    @Autowired
    private AppService appService;

    @PostMapping(value = "/customer/register", produces = "application/json")
    public ResponseEntity saveCustomers(@RequestBody Customer customer) {

        User user = getUser();
        customer.setUser(user);
        customer = appService.save(customer);
        ServerResponse response = new ServerResponse(customer.getAccountNumber(), "Customer Account Created");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/customerDocument/upload")
    public ResponseEntity saveCustomerDocument(MultipartHttpServletRequest multipartHttpServletRequest,
                                               @RequestParam("accountNumber") Long accountNumber,
                                               @RequestParam("documentType") String documentType) {

        CustomerDocument customerDocument = new CustomerDocument();

        List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");
        try {

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                Customer customer = appService.findByAccountNumber(accountNumber);
                customerDocument.setFileName(fileName);
                customerDocument.setDocument(bytes);
                customerDocument.setDocumentType(documentType);
                customerDocument.setCustomer(customer);
                customerDocument = appService.save(customerDocument);

                ServerResponse response = new ServerResponse(customerDocument.getId(), "Customer Account Created");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerResponse response = new ServerResponse(0L, "Document not saved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity login() {

        User user = getUser();
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }


    private String getUsername() {
        User user = getUser();
        return user != null ? user.getUsername() : "";

    }

    private User getUser() {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetail != null)
            return appService.findByUsername(userDetail.getUsername());
        else
            return null;
    }


}
