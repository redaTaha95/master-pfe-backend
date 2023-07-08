package benifit.application.out.http.typeValidation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeValidationResponse {
    private Long id;

    private String type;

    private Boolean matricule;
}
