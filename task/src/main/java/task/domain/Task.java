package task.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;


    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;


    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;


}