package benifit.application.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenifitRequest {

    @NotBlank(message = "details is required")
    private String details;

    @NotBlank(message = "matricule is required")
    private String matricule;

    @NotNull(message = "employeeId is required")
    private Long employeeId;

    @NotNull(message = "typeValidationId is required")
    private Long typeValidationId;
}
