package recruitment_demand.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDemandResponse {

    private Long id;
    private String postTitle;
    private String postDescription;
    private String department;
    private Integer numberOfProfiles;
    private Integer numberOfYearsOfExperience;
    private String levelOfStudies;
    private String statusOfDemand;
    private Date dateOfDemand;

}
