package project.application.in;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    @NotBlank(message = "project name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    private Date startDate;

    private Date endDate;
}
