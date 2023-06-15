package paidTimeOff.domain.out;

import org.springframework.data.jpa.repository.JpaRepository;
import paidTimeOff.domain.PaidTimeOff;

import java.util.List;


public interface PaidTimeOffRepository extends JpaRepository<PaidTimeOff, Long> {
    List<PaidTimeOff> findByEmployeeId(Long employeeId);
}