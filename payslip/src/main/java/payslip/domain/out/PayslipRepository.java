package payslip.domain.out;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payslip.domain.Payslip;

import java.util.List;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {
    List<Payslip> findByPayrollId(Long projectId);
}
