package chatbot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequest {
    @NotBlank(message = "Question text is required")
    private String questionText;

    @NotBlank(message = "Answer text is required")
    private String answerText;

    private Long parentQuestionId;
}
