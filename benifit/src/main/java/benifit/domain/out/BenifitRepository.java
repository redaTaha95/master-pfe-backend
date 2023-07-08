package benifit.domain.out;

import benifit.domain.Benifit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenifitRepository extends JpaRepository<Benifit, Long> {
    List<Benifit> findByEmployeeId(Long employeeId);
}
