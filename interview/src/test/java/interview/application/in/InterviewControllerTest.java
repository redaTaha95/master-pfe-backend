package interview.application.in;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import interview.domain.InterviewResponse;
import interview.domain.out.IInterviewService;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InterviewController.class)
public class InterviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInterviewService interviewService;

    Calendar interviewDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

    @Test
    public void shouldReturnAllInterviews() throws Exception {

        interviewDateCalendar.set(2023, Calendar.JUNE, 7,00,00,00);
        interviewDateCalendar.set(Calendar.MILLISECOND, 0);

        Date interviewDate = interviewDateCalendar.getTime();

        interviewDateCalendar.set(2023, Calendar.JULY, 17, 00, 00, 00);
        interviewDateCalendar.set(Calendar.MILLISECOND, 0);

        Date interviewDate2 = interviewDateCalendar.getTime();

        //Arrange
        List<InterviewResponse> interviewResponse = Arrays.asList(
                new InterviewResponse(1L, "Entretien RH", interviewDate, 1L),
                new InterviewResponse(2L, "Entretien technique", interviewDate2, 1L)
        );

        Mockito.when(interviewService.getAllInterviews()).thenReturn(interviewResponse);

        // Act and Assert
        mockMvc.perform(get("/interviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].interviewTitle").value("Entretien RH"))
                .andExpect(jsonPath("$[0].interviewDate").value("2023-06-07T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].candidateId").value(1L))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].interviewTitle").value("Entretien technique"))
                .andExpect(jsonPath("$[1].interviewDate").value("2023-07-17T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].candidateId").value(1L));
    }

    @Test
    public void shouldReturnInterviewById() throws Exception {

        // Arrange
        Long interviewId = 1L;

        interviewDateCalendar.set(2023, Calendar.JUNE, 7,00,00,00);
        interviewDateCalendar.set(Calendar.MILLISECOND, 0);

        Date interviewDate = interviewDateCalendar.getTime();

        InterviewResponse interviewResponse = new InterviewResponse(1L, "Entretien RH", interviewDate, 1L);

        Mockito.when(interviewService.getInterviewById(interviewId)).thenReturn(interviewResponse);

        // Act and Assert
        mockMvc.perform(get("/interviews/{id}", interviewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(interviewId.intValue()))
                .andExpect(jsonPath("$.interviewTitle").value("Entretien RH"))
                .andExpect(jsonPath("$.interviewDate").value("2023-06-07T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.candidateId").value(1L));
    }

    @Test
    public void shouldCreateInterview() throws Exception {

        interviewDateCalendar.set(2023, Calendar.JUNE, 7,00,00,00);
        interviewDateCalendar.set(Calendar.MILLISECOND, 0);

        Date interviewDate = interviewDateCalendar.getTime();

        // Arrange
        InterviewRequest interviewRequest = new InterviewRequest();
        interviewRequest.setInterviewTitle("Software Developer");
        interviewRequest.setInterviewDate(interviewDate);
        interviewRequest.setCandidateId(1L);

        InterviewResponse createdInterview = new InterviewResponse(1L, "Entretien RH", interviewDate, 1L);

        Mockito.when(interviewService.createInterview(interviewRequest)).thenReturn(createdInterview);

        // Act and Assert
        mockMvc.perform(post("/interviews")
                .content(new ObjectMapper().writeValueAsString(interviewRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.interviewTitle").value("Entretien RH"))
                .andExpect(jsonPath("$.interviewDate").value("2023-06-07T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.candidateId").value(1L));
    }

    @Test
    public void shouldUpdateInterview() throws Exception {

        interviewDateCalendar.set(2023, Calendar.JUNE, 7,00,00,00);
        interviewDateCalendar.set(Calendar.MILLISECOND, 0);

        Date interviewDate = interviewDateCalendar.getTime();

        // Arrange
        Long interviewId = 1L;
        InterviewRequest interviewRequest = new InterviewRequest();
        interviewRequest.setInterviewTitle("Entretien RH");
        interviewRequest.setInterviewDate(interviewDate);
        interviewRequest.setCandidateId(1L);

        InterviewResponse updatedInterview = new InterviewResponse(1L, "Entretien RH", interviewDate, 1L);

        Mockito.when(interviewService.updateInterview(interviewId, interviewRequest)).thenReturn(updatedInterview);

        // Act and Assert
        mockMvc.perform(put("/interviews/{id}", interviewId)
                .content(new ObjectMapper().writeValueAsString(interviewRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(interviewId.intValue()))
                .andExpect(jsonPath("$.interviewTitle").value("Entretien RH"))
                .andExpect(jsonPath("$.interviewDate").value("2023-06-07T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.candidateId").value(1L));
    }

    @Test
    public void shouldDeleteInterview() throws Exception {

        // Arrange
        Long interviewId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/interviews/{id}", interviewId))
                .andExpect(status().isNoContent());

        Mockito.verify(interviewService, times(1)).deleteInterview(interviewId);
    }

}
