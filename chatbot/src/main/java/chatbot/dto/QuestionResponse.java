package chatbot.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {
    private Long id;
    private String questionText;
    private String answerText;
    private Long parentQuestionId;
    private List<QuestionResponse> subQuestions;
}
