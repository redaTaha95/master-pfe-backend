package candidate.application.out.http.recruitment_demand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDemandResponse {

    private Long id;
    private String postTitle;
    private String postDescription;
    private Integer numberOfProfiles;
    private Integer numberOfYearsOfExperience;
    private String levelOfStudies;
    private String statusOfDemand;
    private Date dateOfDemand;
}
