package recruitment_demand.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDemand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "post_title")
    private String postTitle;

    @Column(nullable = false, name = "post_description")
    private String postDescription;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false, name = "number_of_profiles")
    private Integer numberOfProfiles;

    @Column(nullable = false, name = "number_of_years_of_experience")
    private Integer numberOfYearsOfExperience;

    @Column(nullable = false, name = "level_of_studies")
    private String levelOfStudies;

    @Column(nullable = false, name = "status_of_demand")
    private String statusOfDemand;

    @Column(nullable = false, name = "date_of_demand")
    private Date dateOfDemand;
}
