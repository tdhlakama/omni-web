package tk.omi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.omi.model.Customer;
import tk.omi.model.CustomerDocument;
import tk.omi.model.User;
import tk.omi.repository.CustomerDocumentRepository;
import tk.omi.repository.CustomerRepository;
import tk.omi.repository.UserRepository;
import tk.omi.service.AppService;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerDocumentRepository customerDocumentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Customer save(Customer customer) {

        if (customer.getAccountNumber() == null) {
            Long lastAccountNumber = customerRepository.findLastAccountNumber();
            customer.setAccountNumber(lastAccountNumber != null ? lastAccountNumber + 1 : 1000);
        }

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer findByAccountNumber(Long accountNumber) {
        return customerRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Customer> findByUser(User user) {
        return findByUser(user);
    }

    @Override
    public CustomerDocument getCustomerDocument(Long id) {
        return customerDocumentRepository.findOne(id);
    }

    @Override
    public CustomerDocument save(CustomerDocument customerDocument) {
        return customerDocumentRepository.save(customerDocument);
    }

    @Override
    public List<CustomerDocument> findByCustomer(Customer customer) {
        return customerDocumentRepository.findByCustomer(customer);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
