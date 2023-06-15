package paidTimeOff.application.in;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import paidTimeOff.domain.PaidTimeOffResponse;
import paidTimeOff.domain.PaidTimeService;

import java.util.List;

@RestController
@RequestMapping("/paidTimesOff")
@RequiredArgsConstructor
@Validated
public class PaidTimeOffController {
    private final PaidTimeService paidTimeService;

    @PostMapping
    public ResponseEntity<PaidTimeOffResponse> createPaidTimeOff(@RequestBody PaidTimeOffRequest paidTimeOffRequest) {
        PaidTimeOffResponse paidTimeOffResponse = paidTimeService.createPaidTimeOff(paidTimeOffRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(paidTimeOffResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaidTimeOffResponse> getPaidTimeOffById(@PathVariable @Min(value = 1, message = "Invalid paidTimeOff ID") Long id) {
        PaidTimeOffResponse paidTimeOff = paidTimeService.getPaidTimeOffById(id);
        return ResponseEntity.ok(paidTimeOff);
    }

    @GetMapping
    public ResponseEntity<List<PaidTimeOffResponse>> getAllPaidTimesOff() {
        List<PaidTimeOffResponse> paidTimeOffs = paidTimeService.getAllPaidTimeOff();
        return ResponseEntity.ok(paidTimeOffs);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<List<PaidTimeOffResponse>> getPaidTimeOffsByEmployeeId(@PathVariable @Min(value = 1, message = "Invalid Employee ID") Long id) {
        List<PaidTimeOffResponse> paidTimeOffs = paidTimeService.getPaidTimesOffByEmployeeId(id);
        return ResponseEntity.ok(paidTimeOffs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaidTimeOffResponse> updatePaidTimeOff(@PathVariable @Min(value = 1, message = "Invalid paidTimeOff ID") Long id, @Valid @RequestBody PaidTimeOffRequest paidTimeOffRequest) {
        PaidTimeOffResponse updatedPPaidTimeOff = paidTimeService.updatePaidTimeOff(id, paidTimeOffRequest);
        return ResponseEntity.ok(updatedPPaidTimeOff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaidTimeOff(@PathVariable Long id) {
        paidTimeService.deletePaidTimeOff(id);
        return ResponseEntity.noContent().build();
    }

}
