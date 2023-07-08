package payroll.application.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import payroll.domain.PayrollResponse;
import payroll.domain.PayrollService;

import java.util.List;


@RestController
@RequestMapping("/payrolls")
@RequiredArgsConstructor
@Validated
public class PayrollController {
    private final PayrollService payrollService;

    @PostMapping
    public ResponseEntity<PayrollResponse> createPayroll(@RequestBody PayrollRequest payrollRequest) {
        PayrollResponse payrollResponse = payrollService.createPayroll(payrollRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(payrollResponse);
    }

    @GetMapping("/employeesLatestPayroll")
    public ResponseEntity<List<PayrollResponse>> getAllEmployeesWithLatestPayroll() {
        List<PayrollResponse> employeesPayroll = payrollService.getAllEmployeesWithLatestPayroll();

        return ResponseEntity.ok(employeesPayroll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollResponse> getPayrollById(@PathVariable @Min(value = 1, message = "Invalid payroll ID") Long id) {
        PayrollResponse payroll = payrollService.getPayrollById(id);
        return ResponseEntity.ok(payroll);
    }

    @GetMapping
    public ResponseEntity<List<PayrollResponse>> getAllPayrolls() {
        List<PayrollResponse> payrolls = payrollService.getAllPayrolls();
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<List<PayrollResponse>> getPayrollsByEmployeeId(@PathVariable @Min(value = 1, message = "Invalid Employee ID") Long id) {
        List<PayrollResponse> payrolls = payrollService.getPayrollsByEmployeeId(id);
        return ResponseEntity.ok(payrolls);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollResponse> updatePayroll(@PathVariable @Min(value = 1, message = "Invalid payroll ID") Long id, @Valid @RequestBody PayrollRequest payrollRequest) {
        PayrollResponse updatedPayroll = payrollService.updatePayroll(id, payrollRequest);
        return ResponseEntity.ok(updatedPayroll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.noContent().build();
    }

}
