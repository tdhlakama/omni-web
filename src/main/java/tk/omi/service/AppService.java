package tk.omi.service;

import tk.omi.model.Customer;
import tk.omi.model.CustomerDocument;
import tk.omi.model.Role;
import tk.omi.model.User;

import java.util.List;
import java.util.Optional;

public interface AppService {

    Customer save(Customer customer);
    List<Customer> findAllCustomers();
    Customer getCustomer(Long id);
    Customer findByAccountNumber(Long accountNumber);
    List<Customer> findByUser(User user);
    Long countCustomerByUser(User user);

    CustomerDocument getCustomerDocument(Long id);
    CustomerDocument save(CustomerDocument customerDocument);
    List<CustomerDocument> findByCustomer(Customer customer);
    Optional<CustomerDocument> getCustomerDocument(Customer customer, String documentType);

    User save(User user);
    List<User> findAllUsers();
    User getUser(Long id);
    User findByUsername(String username);

    List<Role> findAllRoles();

}