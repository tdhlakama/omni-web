package tk.omi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tk.omi.model.Customer;
import tk.omi.model.CustomerDocument;
import tk.omi.model.User;

import java.util.List;

public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument,Long> {

    List<CustomerDocument> findByCustomer(Customer customer);
}
