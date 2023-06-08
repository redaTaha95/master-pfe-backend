package recruitment_demand.application.in;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import recruitment_demand.domain.RecruitmentDemandResponse;
import recruitment_demand.domain.out.IRecruitmentDemandService;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecruitmentDemandController.class)
public class RecruitmentDemandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRecruitmentDemandService recruitmentDemandService;

    Calendar recruitmentDemandDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

    @Test
    public void shouldReturnAllRecruitmentDemands() throws Exception {

        recruitmentDemandDateCalendar.set(2023, Calendar.JUNE, 7,00,00,00);
        recruitmentDemandDateCalendar.set(Calendar.MILLISECOND, 0);

        Date recruitmentDemandDate = recruitmentDemandDateCalendar.getTime();

        recruitmentDemandDateCalendar.set(2023, Calendar.JULY, 17, 00, 00, 00);
        recruitmentDemandDateCalendar.set(Calendar.MILLISECOND, 0);

        Date recruitmentDemandDate2 = recruitmentDemandDateCalendar.getTime();

        //Arrange
        List<RecruitmentDemandResponse> recruitmentDemandResponse = Arrays.asList(
                new RecruitmentDemandResponse(1L, "Software Developer", "Post Description", 5, 2, "Bac + 5", "En cours", recruitmentDemandDate),
                new RecruitmentDemandResponse(2L, "Software Engineer", "Post Description", 1, 3, "Bac + 3", "Annulé", recruitmentDemandDate2)
        );

        Mockito.when(recruitmentDemandService.getAllRecruitmentDemands()).thenReturn(recruitmentDemandResponse);

        // Act and Assert
        mockMvc.perform(get("/recruitment_demands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].postTitle").value("Software Developer"))
                .andExpect(jsonPath("$[0].postDescription").value("Post Description"))
                .andExpect(jsonPath("$[0].numberOfProfiles").value(5))
                .andExpect(jsonPath("$[0].numberOfYearsOfExperience").value(2))
                .andExpect(jsonPath("$[0].levelOfStudies").value("Bac + 5"))
                .andExpect(jsonPath("$[0].statusOfDemand").value("En cours"))
                .andExpect(jsonPath("$[0].dateOfDemand").value("2023-06-07T00:00:00.000+00:00"))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].postTitle").value("Software Engineer"))
                .andExpect(jsonPath("$[1].postDescription").value("Post Description"))
                .andExpect(jsonPath("$[1].numberOfProfiles").value(1))
                .andExpect(jsonPath("$[1].numberOfYearsOfExperience").value(3))
                .andExpect(jsonPath("$[1].levelOfStudies").value("Bac + 3"))
                .andExpect(jsonPath("$[1].statusOfDemand").value("Annulé"))
                .andExpect(jsonPath("$[1].dateOfDemand").value("2023-07-17T00:00:00.000+00:00"));
    }

    @Test
    public void shouldReturnRecruitmentDemandById() throws Exception {

        // Arrange
        Long recruitmentDemandId = 1L;

        recruitmentDemandDateCalendar.set(2023, Calendar.JUNE, 7,00,00,00);
        recruitmentDemandDateCalendar.set(Calendar.MILLISECOND, 0);

        Date recruitmentDemandDate = recruitmentDemandDateCalendar.getTime();

        RecruitmentDemandResponse recruitmentDemandResponse = new RecruitmentDemandResponse(1L, "Software Developer", "Post Description", 5, 2, "Bac + 5", "En cours", recruitmentDemandDate);

        Mockito.when(recruitmentDemandService.getRecruitmentDemandById(recruitmentDemandId)).thenReturn(recruitmentDemandResponse);

        // Act and Assert
        mockMvc.perform(get("/recruitment_demands/{id}", recruitmentDemandId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recruitmentDemandId.intValue()))
                .andExpect(jsonPath("$.postTitle").value("Software Developer"))
                .andExpect(jsonPath("$.postDescription").value("Post Description"))
                .andExpect(jsonPath("$.numberOfProfiles").value(5))
                .andExpect(jsonPath("$.numberOfYearsOfExperience").value(2))
                .andExpect(jsonPath("$.levelOfStudies").value("Bac + 5"))
                .andExpect(jsonPath("$.statusOfDemand").value("En cours"))
                .andExpect(jsonPath("$.dateOfDemand").value("2023-06-07T00:00:00.000+00:00"));
    }

    @Test
    public void shouldCreateRecruitmentDemand() throws Exception {

        recruitmentDemandDateCalendar.set(2023, Calendar.JUNE, 7,00,00,00);
        recruitmentDemandDateCalendar.set(Calendar.MILLISECOND, 0);

        Date recruitmentDemandDate = recruitmentDemandDateCalendar.getTime();

        // Arrange
        RecruitmentDemandRequest recruitmentDemandRequest = new RecruitmentDemandRequest();
        recruitmentDemandRequest.setPostTitle("Software Developer");
        recruitmentDemandRequest.setPostDescription("Post Description");
        recruitmentDemandRequest.setNumberOfProfiles(5);
        recruitmentDemandRequest.setNumberOfYearsOfExperience(2);
        recruitmentDemandRequest.setLevelOfStudies("Bac + 5");
        recruitmentDemandRequest.setStatusOfDemand("En cours");
        recruitmentDemandRequest.setDateOfDemand(recruitmentDemandDate);

        RecruitmentDemandResponse createdRecruitmentDemand = new RecruitmentDemandResponse(1L, "Software Developer", "Post Description", 5, 2, "Bac + 5", "En cours", recruitmentDemandDate);

        Mockito.when(recruitmentDemandService.createRecruitmentDemand(recruitmentDemandRequest)).thenReturn(createdRecruitmentDemand);

        // Act and Assert
        mockMvc.perform(post("/recruitment_demands")
                .content(new ObjectMapper().writeValueAsString(recruitmentDemandRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.postTitle").value("Software Developer"))
                .andExpect(jsonPath("$.postDescription").value("Post Description"))
                .andExpect(jsonPath("$.numberOfProfiles").value(5))
                .andExpect(jsonPath("$.numberOfYearsOfExperience").value(2))
                .andExpect(jsonPath("$.levelOfStudies").value("Bac + 5"))
                .andExpect(jsonPath("$.statusOfDemand").value("En cours"))
                .andExpect(jsonPath("$.dateOfDemand").value("2023-06-07T00:00:00.000+00:00"));
    }

    @Test
    public void shouldUpdateRecruitmentDemand() throws Exception {

        recruitmentDemandDateCalendar.set(2023, Calendar.JUNE, 7,00,00,00);
        recruitmentDemandDateCalendar.set(Calendar.MILLISECOND, 0);

        Date recruitmentDemandDate = recruitmentDemandDateCalendar.getTime();

        // Arrange
        Long recruitmentDemandId = 1L;
        RecruitmentDemandRequest recruitmentDemandRequest = new RecruitmentDemandRequest();
        recruitmentDemandRequest.setPostTitle("Software Developer");
        recruitmentDemandRequest.setPostDescription("Post Description");
        recruitmentDemandRequest.setNumberOfProfiles(5);
        recruitmentDemandRequest.setNumberOfYearsOfExperience(2);
        recruitmentDemandRequest.setLevelOfStudies("Bac + 5");
        recruitmentDemandRequest.setStatusOfDemand("En cours");
        recruitmentDemandRequest.setDateOfDemand(recruitmentDemandDate);

        RecruitmentDemandResponse updatedRecruitmentDemand = new RecruitmentDemandResponse(recruitmentDemandId, "Software Developer", "Post Description", 5, 2, "Bac + 5", "En cours", recruitmentDemandDate);

        Mockito.when(recruitmentDemandService.updateRecruitmentDemand(recruitmentDemandId, recruitmentDemandRequest)).thenReturn(updatedRecruitmentDemand);

        // Act and Assert
        mockMvc.perform(put("/recruitment_demands/{id}", recruitmentDemandId)
                .content(new ObjectMapper().writeValueAsString(recruitmentDemandRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recruitmentDemandId.intValue()))
                .andExpect(jsonPath("$.postTitle").value("Software Developer"))
                .andExpect(jsonPath("$.postDescription").value("Post Description"))
                .andExpect(jsonPath("$.numberOfProfiles").value(5))
                .andExpect(jsonPath("$.numberOfYearsOfExperience").value(2))
                .andExpect(jsonPath("$.levelOfStudies").value("Bac + 5"))
                .andExpect(jsonPath("$.statusOfDemand").value("En cours"))
                .andExpect(jsonPath("$.dateOfDemand").value("2023-06-07T00:00:00.000+00:00"));
    }

    @Test
    public void shouldDeleteRecruitmentDemand() throws Exception {

        // Arrange
        Long recruitmentDemandId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/recruitment_demands/{id}", recruitmentDemandId))
                .andExpect(status().isNoContent());

        Mockito.verify(recruitmentDemandService, times(1)).deleteRecruitmentDemand(recruitmentDemandId);
    }
}
