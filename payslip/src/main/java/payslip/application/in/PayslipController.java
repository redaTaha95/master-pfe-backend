package payslip.application.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import payslip.domain.PayslipResponse;
import payslip.domain.PayslipService;
import java.util.List;

@RestController
@RequestMapping("/payslips")
@RequiredArgsConstructor
@Validated
public class PayslipController {

    private final PayslipService payslipService;

    @PostMapping
    public ResponseEntity<PayslipResponse> createPayslip(@RequestBody PayslipRequest payslipRequest) {
        PayslipResponse payslipResponse = payslipService.createPayslip(payslipRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(payslipResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayslipResponse> getPayslipById(@PathVariable @Min(value = 1, message = "Invalid payslip ID") Long id) {
        PayslipResponse payslip = payslipService.getPayslipById(id);
        return ResponseEntity.ok(payslip);
    }

    @GetMapping
    public ResponseEntity<List<PayslipResponse>> getAllPayslips() {
        List<PayslipResponse> payslips = payslipService.getAllPayslips();
        return ResponseEntity.ok(payslips);
    }

    @GetMapping("payroll/{id}")
    public ResponseEntity<List<PayslipResponse>> getPayslipsByPayrollId(@PathVariable @Min(value = 1, message = "Invalid payroll ID") Long id) {
        List<PayslipResponse> payslips = payslipService.getPayslipsByPayrollId(id);
        return ResponseEntity.ok(payslips);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayslipResponse> updatePayslip(@PathVariable @Min(value = 1, message = "Invalid payslip ID") Long id, @Valid @RequestBody PayslipRequest payslipRequest) {
        PayslipResponse updatedPayslip = payslipService.updatePayslip(id, payslipRequest);
        return ResponseEntity.ok(updatedPayslip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayslip(@PathVariable Long id) {
        payslipService.deletePayslip(id);
        return ResponseEntity.noContent().build();
    }

}
