package chatbot;

import chatbot.dto.QuestionRequest;
import chatbot.dto.QuestionResponse;
import chatbot.exception.QuestionNotFoundException;
import chatbot.model.Question;
import chatbot.repository.QuestionRepository;
import chatbot.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void testAddQuestion() {
        // Create a test question request
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestionText("What is the meaning of life?");
        questionRequest.setAnswerText("42");

        // Create a mock parent question
        Question parentQuestion = new Question();
        parentQuestion.setId(1L);

        // Create a mock saved question
        Question savedQuestion = new Question();
        savedQuestion.setId(2L);
        savedQuestion.setQuestionText(questionRequest.getQuestionText());
        savedQuestion.setAnswerText(questionRequest.getAnswerText());
        savedQuestion.setParentQuestion(parentQuestion);

        // Mock the question repository save operation
        when(questionRepository.save(any(Question.class))).thenReturn(savedQuestion);

        // Add the question using the service
        QuestionResponse questionResponse = questionService.addQuestion(questionRequest);

        // Verify the question repository save operation
        verify(questionRepository, times(1)).save(any(Question.class));

        // Verify the question response
        assertNotNull(questionResponse);
        assertEquals(savedQuestion.getId(), questionResponse.getId());
        assertEquals(savedQuestion.getQuestionText(), questionResponse.getQuestionText());
        assertEquals(savedQuestion.getAnswerText(), questionResponse.getAnswerText());
        assertEquals(savedQuestion.getParentQuestion().getId(), questionResponse.getParentQuestionId());
    }



    @Test
    void testAddQuestionWithoutParent() {
        // Create a test question request
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestionText("What is the meaning of life?");
        questionRequest.setAnswerText("42");

        // Mock the question repository save operation
        when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> {
            Question savedQuestion = invocation.getArgument(0);
            savedQuestion.setId(1L);
            return savedQuestion;
        });

        // Add the question using the service
        QuestionResponse questionResponse = questionService.addQuestion(questionRequest);

        // Verify the question response
        assertNotNull(questionResponse);
        assertEquals(1L, questionResponse.getId());
        assertEquals(questionRequest.getQuestionText(), questionResponse.getQuestionText());
        assertEquals(questionRequest.getAnswerText(), questionResponse.getAnswerText());
        assertNull(questionResponse.getParentQuestionId());

        // Verify the question repository save operation
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void testAddQuestionWithMissingParent() {
        // Create a test question request
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestionText("What is the meaning of life?");
        questionRequest.setAnswerText("42");
        questionRequest.setParentQuestionId(1L);

        // Mock the parent question retrieval to return null
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that a QuestionNotFoundException is thrown when adding the question
        assertThrows(QuestionNotFoundException.class, () -> questionService.addQuestion(questionRequest));

        // Verify the parent question retrieval
        verify(questionRepository, times(1)).findById(1L);
        // Verify that the question repository save operation is not invoked
        verify(questionRepository, never()).save(any(Question.class));
    }

    @Test
    void testGetAllQuestions() {
        // Create a list of mock questions
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setId(1L);
        question1.setQuestionText("Question 1");
        question1.setAnswerText("Answer 1");
        questions.add(question1);
        Question question2 = new Question();
        question2.setId(2L);
        question2.setQuestionText("Question 2");
        question2.setAnswerText("Answer 2");
        questions.add(question2);

        // Mock the question repository to return the list of questions
        when(questionRepository.findAll()).thenReturn(questions);

        // Get all questions using the service
        List<QuestionResponse> questionResponses = questionService.getAllQuestions();

        // Verify the question responses
        assertNotNull(questionResponses);
        assertEquals(2, questionResponses.size());
        QuestionResponse questionResponse1 = questionResponses.get(0);
        assertEquals(question1.getId(), questionResponse1.getId());
        assertEquals(question1.getQuestionText(), questionResponse1.getQuestionText());
        assertEquals(question1.getAnswerText(), questionResponse1.getAnswerText());
        assertNull(questionResponse1.getParentQuestionId());
        QuestionResponse questionResponse2 = questionResponses.get(1);
        assertEquals(question2.getId(), questionResponse2.getId());
        assertEquals(question2.getQuestionText(), questionResponse2.getQuestionText());
        assertEquals(question2.getAnswerText(), questionResponse2.getAnswerText());
        assertNull(questionResponse2.getParentQuestionId());

        // Verify the question repository findAll operation
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    void testGetQuestionById() {
        // Create a mock question
        Question question = new Question();
        question.setId(1L);
        question.setQuestionText("What is the meaning of life?");
        question.setAnswerText("42");
        question.setSubQuestions(List.of());

        // Mock the question repository to return the question
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        // Get the question by ID using the service
        QuestionResponse questionResponse = questionService.getQuestionById(1L);

        // Verify the question response
        assertNotNull(questionResponse);
        assertEquals(question.getId(), questionResponse.getId());
        assertEquals(question.getQuestionText(), questionResponse.getQuestionText());
        assertEquals(question.getAnswerText(), questionResponse.getAnswerText());
        assertNull(questionResponse.getParentQuestionId());

        // Verify the question repository findById operation
        verify(questionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetQuestionByIdNotFound() {
        // Mock the question repository to return an empty optional
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that a QuestionNotFoundException is thrown when getting the question by ID
        assertThrows(QuestionNotFoundException.class, () -> questionService.getQuestionById(1L));

        // Verify the question repository findById operation
        verify(questionRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateQuestion() {
        // Create a test question request
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestionText("Updated question");
        questionRequest.setAnswerText("Updated answer");

        // Create a mock question
        Question question = new Question();
        question.setId(1L);
        question.setQuestionText("Old question");
        question.setAnswerText("Old answer");

        // Mock the question repository to return the question
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        // Mock the question repository save operation
        when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Update the question using the service
        QuestionResponse updatedQuestion = questionService.updateQuestion(1L, questionRequest);

        // Verify the updated question response
        assertNotNull(updatedQuestion);
        assertEquals(question.getId(), updatedQuestion.getId());
        assertEquals(questionRequest.getQuestionText(), updatedQuestion.getQuestionText());
        assertEquals(questionRequest.getAnswerText(), updatedQuestion.getAnswerText());
        assertNull(updatedQuestion.getParentQuestionId());

        // Verify the question repository findById operation
        verify(questionRepository, times(1)).findById(1L);
        // Verify the question repository save operation
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void testUpdateQuestionNotFound() {
        // Create a test question request
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestionText("Updated question");
        questionRequest.setAnswerText("Updated answer");

        // Mock the question repository to return an empty optional
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that a QuestionNotFoundException is thrown when updating the question
        assertThrows(QuestionNotFoundException.class, () -> questionService.updateQuestion(1L, questionRequest));

        // Verify the question repository findById operation
        verify(questionRepository, times(1)).findById(1L);
        // Verify that the question repository save operation is not invoked
        verify(questionRepository, never()).save(any(Question.class));
    }

    @Test
    void testDeleteQuestion() {
        // Delete the question using the service
        questionService.deleteQuestion(1L);

        // Verify the question repository deleteById operation
        verify(questionRepository, times(1)).deleteById(1L);
    }
}
