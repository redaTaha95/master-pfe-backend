package candidate.application.in;

import candidate.domain.CandidateResponse;
import candidate.domain.out.ICandidateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
@Validated
public class CandidateController {

    private final ICandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidateResponse>> getAllCandidates() {

        List<CandidateResponse> candidates = candidateService.getAllCandidates();

        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable @Min(value = 1, message = "Invalid candidate ID") Long id) {

        CandidateResponse candidate = candidateService.getCandidateById(id);

        return ResponseEntity.ok(candidate);
    }

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(@Valid @RequestBody CandidateRequest candidateRequest) {

        CandidateResponse createdCandidate = candidateService.createCandidate(candidateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable @Min(value = 1, message = "Invalid candidate ID") Long id, @Valid @RequestBody CandidateRequest candidateRequest) {

        CandidateResponse updatedCandidate = candidateService.updateCandidate(id, candidateRequest);

        return ResponseEntity.ok(updatedCandidate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {

        candidateService.deleteCandidate(id);

        return ResponseEntity.noContent().build();
    }

}
