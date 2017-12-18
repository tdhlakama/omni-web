package tk.omi.service;

import tk.omi.model.Customer;
import tk.omi.model.CustomerDocument;
import tk.omi.model.User;

import java.util.List;

public interface AppService {

    Customer save(Customer customer);
    List<Customer> findAllCustomers();
    Customer getCustomer(Long id);
    Customer findByAccountNumber(Long accountNumber);
    List<Customer> findByUser(User user);

    CustomerDocument getCustomerDocument(Long id);
    CustomerDocument save(CustomerDocument customerDocument);
    List<CustomerDocument> findByCustomer(Customer customer);

    User save(User user);
    List<User> findAllUsers();
    User getUser(Long id);
    User findByUsername(String username);

}