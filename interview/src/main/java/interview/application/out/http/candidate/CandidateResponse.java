package interview.application.out.http.candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String sector;
    private Integer numberOfYearsOfExperience;
    private String levelOfStudies;
    private Long recruitmentDemandId;
}
