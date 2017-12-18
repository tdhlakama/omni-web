package tk.omi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tk.omi.model.Customer;
import tk.omi.model.User;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByUser(User user);

    @Query("select max(c.accountNumber) from Customer c")
    Long findLastAccountNumber();

    Customer findByAccountNumber(Long accountNumber);

}
