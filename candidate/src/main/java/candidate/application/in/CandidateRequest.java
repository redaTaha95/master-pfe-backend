package candidate.application.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Sector is required")
    private String sector;

    @NotNull(message = "Number of years of experience is required")
    private Integer numberOfYearsOfExperience;

    @NotBlank(message = "Level of studies is required")
    private String levelOfStudies;

    @NotNull(message = "Recruitment demand id is required")
    private Long recruitmentDemandId;

}
