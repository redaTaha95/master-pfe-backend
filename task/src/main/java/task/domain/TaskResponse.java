package task.domain;



import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private Long id;


    private String name;


    private String description;


    private Long projectId;


    private Date startDate;


    private Date endDate;
}
