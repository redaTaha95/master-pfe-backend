package recruitment_demand.application.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class RecruitmentDemandRequest {

    @NotBlank(message = "Post title is required")
    private String postTitle;

    @NotBlank(message = "Post description is required")
    private String postDescription;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "Number of profiles is required")
    private Integer numberOfProfiles;

    @NotNull(message = "Number of years of experience is required")
    private Integer numberOfYearsOfExperience;

    @NotBlank(message = "Level of studies is required")
    private String levelOfStudies;

    @NotBlank(message = "Status of demand is required")
    private String statusOfDemand;

    @NotNull(message = "Date of demand is required")
    private Date dateOfDemand;
}
