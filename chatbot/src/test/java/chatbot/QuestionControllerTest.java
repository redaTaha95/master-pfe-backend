package chatbot;

import chatbot.controller.QuestionController;
import chatbot.dto.QuestionRequest;
import chatbot.dto.QuestionResponse;
import chatbot.exception.QuestionNotFoundException;
import chatbot.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Test
    void testAddQuestion() throws Exception {
        // Create a test question request
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestionText("What is the meaning of life?");
        questionRequest.setAnswerText("42");

        // Create a mock question response
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(1L);
        questionResponse.setQuestionText(questionRequest.getQuestionText());
        questionResponse.setAnswerText(questionRequest.getAnswerText());

        // Mock the question service method
        when(questionService.addQuestion(any(QuestionRequest.class))).thenReturn(questionResponse);

        // Perform the POST request
        mockMvc.perform(post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(questionRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(questionResponse.getId()))
                .andExpect(jsonPath("$.questionText").value(questionResponse.getQuestionText()))
                .andExpect(jsonPath("$.answerText").value(questionResponse.getAnswerText()));

        // Verify the question service method invocation
        verify(questionService, times(1)).addQuestion(any(QuestionRequest.class));
    }

    @Test
    void testGetAllQuestions() throws Exception {
        // Create mock question responses
        List<QuestionResponse> questionResponses = Arrays.asList(
                new QuestionResponse(1L, "Question 1", "Answer 1", null, null),
                new QuestionResponse(2L, "Question 2", "Answer 2", null, null)
        );

        // Mock the question service method
        when(questionService.getAllQuestions()).thenReturn(questionResponses);

        // Perform the GET request
        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(questionResponses.size()))
                .andExpect(jsonPath("$[0].id").value(questionResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].questionText").value(questionResponses.get(0).getQuestionText()))
                .andExpect(jsonPath("$[0].answerText").value(questionResponses.get(0).getAnswerText()))
                .andExpect(jsonPath("$[0].parentQuestionId").value(questionResponses.get(0).getParentQuestionId()))
                .andExpect(jsonPath("$[1].id").value(questionResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].questionText").value(questionResponses.get(1).getQuestionText()))
                .andExpect(jsonPath("$[1].answerText").value(questionResponses.get(1).getAnswerText()))
                .andExpect(jsonPath("$[1].parentQuestionId").value(questionResponses.get(1).getParentQuestionId()));

        // Verify the question service method invocation
        verify(questionService, times(1)).getAllQuestions();
    }

    @Test
    void testGetQuestionById() throws Exception {
        Long questionId = 1L;

        // Create a mock question response
        QuestionResponse questionResponse = new QuestionResponse(questionId, "Question 1", "Answer 1", null, null);

        // Mock the question service method
        when(questionService.getQuestionById(questionId)).thenReturn(questionResponse);

        // Perform the GET request
        mockMvc.perform(get("/questions/{id}", questionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(questionResponse.getId()))
                .andExpect(jsonPath("$.questionText").value(questionResponse.getQuestionText()))
                .andExpect(jsonPath("$.answerText").value(questionResponse.getAnswerText()))
                .andExpect(jsonPath("$.parentQuestionId").value(questionResponse.getParentQuestionId()));

        // Verify the question service method invocation
        verify(questionService, times(1)).getQuestionById(questionId);
    }

    @Test
    void testUpdateQuestion() throws Exception {
        Long questionId = 1L;

        // Create a test question request
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestionText("Updated question");
        questionRequest.setAnswerText("Updated answer");

        // Create a mock question response
        QuestionResponse questionResponse = new QuestionResponse(questionId, questionRequest.getQuestionText(),
                questionRequest.getAnswerText(), null, null);

        // Mock the question service method
        when(questionService.updateQuestion(eq(questionId), any(QuestionRequest.class))).thenReturn(questionResponse);

        // Perform the PUT request
        mockMvc.perform(put("/questions/{id}", questionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(questionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(questionResponse.getId()))
                .andExpect(jsonPath("$.questionText").value(questionResponse.getQuestionText()))
                .andExpect(jsonPath("$.answerText").value(questionResponse.getAnswerText()))
                .andExpect(jsonPath("$.parentQuestionId").value(questionResponse.getParentQuestionId()));

        // Verify the question service method invocation
        verify(questionService, times(1)).updateQuestion(eq(questionId), any(QuestionRequest.class));
    }

    @Test
    void testDeleteQuestion() throws Exception {
        Long questionId = 1L;

        // Perform the DELETE request
        mockMvc.perform(delete("/questions/{id}", questionId))
                .andExpect(status().isNoContent());

        // Verify the question service method invocation
        verify(questionService, times(1)).deleteQuestion(questionId);
    }

    @Test
    void testHandleQuestionNotFoundException() throws Exception {
        Long questionId = 1L;

        // Mock the question service method to throw a QuestionNotFoundException
        when(questionService.getQuestionById(questionId))
                .thenThrow(new QuestionNotFoundException("Question not found with id: " + questionId));

        // Perform the GET request
        mockMvc.perform(get("/questions/{id}", questionId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Question not found with id: " + questionId));

        // Verify the question service method invocation
        verify(questionService, times(1)).getQuestionById(questionId);
    }

    // Helper method to convert an object to JSON string
    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
