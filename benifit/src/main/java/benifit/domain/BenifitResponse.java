package benifit.domain;


import benifit.application.out.http.employee.EmployeeResponse;
import benifit.application.out.http.typeValidation.TypeValidationResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenifitResponse {
    private Long id;

    private String details;

    private String matricule;

    private EmployeeResponse employee;

    private TypeValidationResponse typeValidationId;
}
