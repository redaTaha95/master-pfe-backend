package recruitment_demand.application.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recruitment_demand.domain.RecruitmentDemandResponse;
import recruitment_demand.domain.out.IRecruitmentDemandService;

import java.util.List;

@RestController
@RequestMapping("/recruitment_demands")
@RequiredArgsConstructor
@Validated
public class RecruitmentDemandController {

    private final IRecruitmentDemandService recruitmentDemandService;

    @GetMapping
    public ResponseEntity<List<RecruitmentDemandResponse>> getAllRecruitmentDemands() {

        List<RecruitmentDemandResponse> recruitmentDemands = recruitmentDemandService.getAllRecruitmentDemands();

        return ResponseEntity.ok(recruitmentDemands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentDemandResponse> getRecruitmentDemandById(@PathVariable @Min(value = 1, message = "Invalid recruitment demand ID") Long id) {

        RecruitmentDemandResponse recruitmentDemand = recruitmentDemandService.getRecruitmentDemandById(id);

        return ResponseEntity.ok(recruitmentDemand);
    }

    @PostMapping
    public ResponseEntity<RecruitmentDemandResponse> createRecruitmentDemand(@Valid @RequestBody RecruitmentDemandRequest recruitmentDemandRequestRequest) {

        RecruitmentDemandResponse createdRecruitmentDemand = recruitmentDemandService.createRecruitmentDemand(recruitmentDemandRequestRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecruitmentDemand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruitmentDemandResponse> updateRecruitmentDemand(@PathVariable @Min(value = 1, message = "Invalid recruitment demand ID") Long id, @Valid @RequestBody RecruitmentDemandRequest recruitmentDemandRequest) {

        RecruitmentDemandResponse updatedRecruitmentDemand = recruitmentDemandService.updateRecruitmentDemand(id, recruitmentDemandRequest);

        return ResponseEntity.ok(updatedRecruitmentDemand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruitmentDemand(@PathVariable Long id) {

        recruitmentDemandService.deleteRecruitmentDemand(id);

        return ResponseEntity.noContent().build();
    }
}
