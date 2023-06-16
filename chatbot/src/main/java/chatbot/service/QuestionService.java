package chatbot.service;

import chatbot.dto.QuestionRequest;
import chatbot.dto.QuestionResponse;
import chatbot.exception.QuestionNotFoundException;
import chatbot.model.Question;
import chatbot.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionResponse addQuestion(QuestionRequest questionRequest) {
        Question question = Question.builder()
                .questionText(questionRequest.getQuestionText())
                .answerText(questionRequest.getAnswerText())
                .build();

        if (questionRequest.getParentQuestionId() != null) {
            Optional<Question> parentQuestion = questionRepository.findById(questionRequest.getParentQuestionId());
            parentQuestion.ifPresentOrElse(question::setParentQuestion,
                    () -> {throw new QuestionNotFoundException("Parent question not found with id: " +questionRequest.getParentQuestionId());});
        }

        Question savedQuestion = questionRepository.save(question);

        return mapToQuestionResponse(savedQuestion);
    }

    public List<QuestionResponse> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());
    }

    public QuestionResponse getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        return question.map(this::mapToQuestionResponseWithSubQuestions)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + id));
    }

    public QuestionResponse updateQuestion(Long id, QuestionRequest questionRequest) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setQuestionText(questionRequest.getQuestionText());
            question.setAnswerText(questionRequest.getAnswerText());

            if (questionRequest.getParentQuestionId() != null) {
                Optional<Question> parentQuestion = questionRepository.findById(questionRequest.getParentQuestionId());
                parentQuestion.ifPresentOrElse(question::setParentQuestion,
                        () -> {throw new QuestionNotFoundException("Parent question not found with id: " +questionRequest.getParentQuestionId());});
            } else {
                question.setParentQuestion(null);
            }

            Question updatedQuestion = questionRepository.save(question);

            return mapToQuestionResponse(updatedQuestion);
        } else {
            throw new QuestionNotFoundException("Question not found with id: " + id);
        }
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    private QuestionResponse mapToQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .answerText(question.getAnswerText())
                .parentQuestionId(question.getParentQuestion() != null ? question.getParentQuestion().getId() : null)
                .build();
    }

    private QuestionResponse mapToQuestionResponseWithSubQuestions(Question question) {
        List<QuestionResponse> subQuestions = question.getSubQuestions().stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());

        return QuestionResponse.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .answerText(question.getAnswerText())
                .parentQuestionId(question.getParentQuestion() != null ? question.getParentQuestion().getId() : null)
                .subQuestions(subQuestions)
                .build();
    }
}
