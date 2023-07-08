package chatbot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String questionText;

    @Column(nullable = false, columnDefinition = "text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "parent_question_id")
    private Question parentQuestion;

    @OneToMany(mappedBy = "parentQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> subQuestions;
}
