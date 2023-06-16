package interview.application.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class InterviewRequest {

    @NotBlank(message = "Interview title is required")
    private String interviewTitle;

    @NotNull(message = "Interview date is required")
    private Date interviewDate;

    @NotNull(message = "Candidate id is required")
    private Long candidateId;
}
