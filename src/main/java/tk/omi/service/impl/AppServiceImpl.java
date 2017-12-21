package tk.omi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.omi.model.Customer;
import tk.omi.model.CustomerDocument;
import tk.omi.model.Role;
import tk.omi.model.User;
import tk.omi.repository.CustomerDocumentRepository;
import tk.omi.repository.CustomerRepository;
import tk.omi.repository.RoleRepository;
import tk.omi.repository.UserRepository;
import tk.omi.service.AppService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService, UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerDocumentRepository customerDocumentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

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
        return customerRepository.findByUser(user);
    }

    public Long countCustomerByUser(User user) {
        return customerRepository.countCustomerByUser(user);
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
    public Optional<CustomerDocument> getCustomerDocument(Customer customer, String documentType) {
        return customerDocumentRepository.findByCustomer(customer).stream().findFirst();
    }

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }


}
