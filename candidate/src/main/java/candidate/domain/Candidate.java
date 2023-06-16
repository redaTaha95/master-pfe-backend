package candidate.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String sector;

    @Column(nullable = false, name = "number_of_years_of_experience")
    private Integer numberOfYearsOfExperience;

    @Column(nullable = false, name = "level_of_studies")
    private String levelOfStudies;

    @Column(nullable = false, name = "recruitment_demand_id")
    private Long recruitmentDemandId;
}