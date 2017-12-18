package tk.omi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tk.omi.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
