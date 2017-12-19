package tk.omi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tk.omi.model.Role;
import tk.omi.model.User;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
