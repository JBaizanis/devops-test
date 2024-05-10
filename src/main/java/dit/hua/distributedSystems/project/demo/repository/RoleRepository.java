package dit.hua.distributedSystems.project.demo.repository;

import dit.hua.distributedSystems.project.demo.entity.Role;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Hidden
public interface RoleRepository  extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(String role);
}
