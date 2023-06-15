package typeValidation.domain;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeValidationResponse {
    private Long id;

    private String type;

    private Boolean matricule;
}
