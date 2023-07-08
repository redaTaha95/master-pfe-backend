package benifit.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Benifit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String details;

    private String matricule;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private Long typeValidationId;
}
