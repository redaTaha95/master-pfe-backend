package payroll.domain.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.domain.Payroll;

import java.util.List;


@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findByEmployeeId(Long employeeId);
    Payroll findFirstByEmployeeIdOrderByPayrollDateDesc(Long employeeId);
}
