package benifit.application.in;

import benifit.domain.BenifitResponse;
import benifit.domain.BenifitService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benifits")
@RequiredArgsConstructor
@Validated
public class BenifitController {
    private final BenifitService benifitService;

    @PostMapping
    public ResponseEntity<BenifitResponse> createBenifit(@RequestBody BenifitRequest benifitRequest) {
        BenifitResponse benifitResponse = benifitService.createBenifit(benifitRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(benifitResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BenifitResponse> getBenifitById(@PathVariable @Min(value = 1, message = "Invalid benifit ID") Long id) {
        BenifitResponse benifit = benifitService.getBenifitsById(id);
        return ResponseEntity.ok(benifit);
    }

    @GetMapping
    public ResponseEntity<List<BenifitResponse>> getAllBenifits() {
        List<BenifitResponse> benifits = benifitService.getAllBenifits();
        return ResponseEntity.ok(benifits);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<List<BenifitResponse>> getBenifitByEmployeeId(@PathVariable @Min(value = 1, message = "Invalid Employee ID") Long id) {
        List<BenifitResponse> benifits = benifitService.getBenifitsByEmployeeId(id);
        return ResponseEntity.ok(benifits);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BenifitResponse> updateBenifit(@PathVariable @Min(value = 1, message = "Invalid benifit ID") Long id, @Valid @RequestBody BenifitRequest benifitRequest) {
        BenifitResponse updatedbenifit = benifitService.updateBenifit(id, benifitRequest);
        return ResponseEntity.ok(updatedbenifit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBenifit(@PathVariable Long id) {
        benifitService.deleteBenifit(id);
        return ResponseEntity.noContent().build();
    }

}
