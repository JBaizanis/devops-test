package dit.hua.distributedSystems.project.demo.repository;

import dit.hua.distributedSystems.project.demo.entity.Application;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface ApplicationRepository  extends JpaRepository<Application, Integer> {
}
