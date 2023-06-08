package interview.application.in;

import interview.domain.InterviewResponse;
import interview.domain.out.IInterviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
@Validated
public class InterviewController {

    private final IInterviewService interviewService;

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getAllInterviews() {

        List<InterviewResponse> interviews = interviewService.getAllInterviews();

        return ResponseEntity.ok(interviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponse> getInterviewById(@PathVariable @Min(value = 1, message = "Invalid interview ID") Long id) {

        InterviewResponse interview = interviewService.getInterviewById(id);

        return ResponseEntity.ok(interview);
    }

    @PostMapping
    public ResponseEntity<InterviewResponse> createInterview(@Valid @RequestBody InterviewRequest interviewRequest) {

        InterviewResponse createdInterview = interviewService.createInterview(interviewRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdInterview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewResponse> updateInterview(@PathVariable @Min(value = 1, message = "Invalid interview ID") Long id, @Valid @RequestBody InterviewRequest interviewRequest) {

        InterviewResponse updatedInterview = interviewService.updateInterview(id, interviewRequest);

        return ResponseEntity.ok(updatedInterview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {

        interviewService.deleteInterview(id);

        return ResponseEntity.noContent().build();
    }
}
