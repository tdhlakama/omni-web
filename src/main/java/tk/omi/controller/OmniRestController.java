package tk.omi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Optional;

@RestController
@RequestMapping("/api")
@MultipartConfig(fileSizeThreshold = 20971520)
public class OmniRestController {

    @Autowired
    private AppService appService;

    @PostMapping(value = "/customer/register", produces = "application/json")
    public ResponseEntity saveCustomer(@RequestBody Customer customer) {

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

                ServerResponse response = new ServerResponse(customerDocument.getId(), "Customer Document Uploaded");
                return new ResponseEntity<>(response, HttpStatus.OK);
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

    private User getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return appService.findByUsername(authentication.getName());
        } else {
            return null;
        }

    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getCustomerSupportDocumentByDocumentType(@RequestParam("id") Long id, @RequestParam("documentType") String documentType) {
        HttpHeaders headers = new HttpHeaders();

        Customer customer = appService.getCustomer(id);
        Optional<CustomerDocument> documentOptional = appService
                .getCustomerDocument(customer, documentType);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(documentOptional.get().getDocument(), headers, HttpStatus.OK);
        return responseEntity;
    }


}
