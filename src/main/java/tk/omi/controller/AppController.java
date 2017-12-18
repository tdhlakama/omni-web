package tk.omi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tk.omi.model.Customer;
import tk.omi.model.CustomerDocument;
import tk.omi.service.AppService;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private AppService appService;

    @GetMapping(value = "/test", produces = "application/json")
    public ResponseEntity test() {
        ServerResponse response = new ServerResponse(1L, "Connection Successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/customer/register", produces = "application/json")
    public ResponseEntity saveCustomers(@RequestBody Customer customer) {
        customer = appService.save(customer);
        ServerResponse response = new ServerResponse(customer.getAccountNumber(), "Customer Account Created");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/customerDocument/upload")
    public ResponseEntity saveCustomerDocument(MultipartHttpServletRequest multipartHttpServletRequest,
                                               @RequestParam("accountNumber") Long accountNumber,
                                               @RequestParam("documentType") String documentType) {

        CustomerDocument customerDocument = new CustomerDocument();

        List<MultipartFile> files = multipartHttpServletRequest.getFiles("name");
        try {

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();

                byte[] bytes = file.getBytes();
                Customer customer = appService.findByAccountNumber(accountNumber);
                customerDocument.setFileName(fileName);
                customerDocument.setDocumentType(documentType);
                customerDocument.setCustomer(customer);
                customerDocument = appService.save(customerDocument);
                customerDocument.setDocument(bytes);

                ServerResponse response = new ServerResponse(customerDocument.getId(), "Customer Account Created");
                return ResponseEntity.ok(response);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerResponse response = new ServerResponse(0L, "Document not saved");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam("username") String username,
                                @RequestParam(("password")) String password) {
        return ResponseEntity.ok("Connection Successful");
    }

}
