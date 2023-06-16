package candidate.application.in;

import candidate.domain.CandidateResponse;
import candidate.domain.out.ICandidateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CandidateController.class)
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICandidateService candidateService;

    @Test
    public void shouldReturnAllCandidates() throws Exception {

        // Arrange
        List<CandidateResponse> candidateResponses = Arrays.asList(
                new CandidateResponse(1L, "John", "Doe", "john.doe@example.com", "0099887766", "Casablanca, Maroc", "Commerce", 2, "Bac + 2", 1L),
                new CandidateResponse(2L, "Jane", "Smith", "jane.smith@example.com", "0102030405", "Meknes, Maroc", "Droit", 5, "Bac + 5", 1L)
        );

        Mockito.when(candidateService.getAllCandidates()).thenReturn(candidateResponses);

        // Act and Assert
        mockMvc.perform(get("/candidates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].phone").value("0099887766"))
                .andExpect(jsonPath("$[0].address").value("Casablanca, Maroc"))
                .andExpect(jsonPath("$[0].sector").value("Commerce"))
                .andExpect(jsonPath("$[0].numberOfYearsOfExperience").value(2))
                .andExpect(jsonPath("$[0].levelOfStudies").value("Bac + 2"))
                .andExpect(jsonPath("$[0].recruitmentDemandId").value(1))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$[1].phone").value("0102030405"))
                .andExpect(jsonPath("$[1].address").value("Meknes, Maroc"))
                .andExpect(jsonPath("$[1].sector").value("Droit"))
                .andExpect(jsonPath("$[1].numberOfYearsOfExperience").value(5))
                .andExpect(jsonPath("$[1].levelOfStudies").value("Bac + 5"))
                .andExpect(jsonPath("$[1].recruitmentDemandId").value(1L));
    }

    @Test
    public void shouldReturnCandidateById() throws Exception {

        // Arrange
        Long candidateId = 1L;
        CandidateResponse candidateResponse = new CandidateResponse(candidateId, "John", "Doe", "john.doe@example.com", "0666666666", "Marseille, France", "Audit", 2, "Bac + 2", 1L);

        Mockito.when(candidateService.getCandidateById(candidateId)).thenReturn(candidateResponse);

        // Act and Assert
        mockMvc.perform(get("/candidates/{id}", candidateId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(candidateId.intValue()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phone").value("0666666666"))
                .andExpect(jsonPath("$.address").value("Marseille, France"))
                .andExpect(jsonPath("$.sector").value("Audit"))
                .andExpect(jsonPath("$.numberOfYearsOfExperience").value(2))
                .andExpect(jsonPath("$.levelOfStudies").value("Bac + 2"))
                .andExpect(jsonPath("$.recruitmentDemandId").value(1L));
    }

    @Test
    public void shouldCreateCandidate() throws Exception {

        // Arrange
        CandidateRequest candidateRequest = new CandidateRequest();
        candidateRequest.setFirstName("John");
        candidateRequest.setLastName("Doe");
        candidateRequest.setEmail("john.doe@example.com");
        candidateRequest.setPhone("0699515293");
        candidateRequest.setAddress("Marseille, France");
        candidateRequest.setSector("Logistique");
        candidateRequest.setNumberOfYearsOfExperience(3);
        candidateRequest.setLevelOfStudies("Bac + 2");
        candidateRequest.setRecruitmentDemandId(1L);

        CandidateResponse createdCandidate = new CandidateResponse(1L, "John", "Doe", "john.doe@example.com", "0699515293", "Marseille, France", "Logistique", 3, "Bac + 2", 1L);

        Mockito.when(candidateService.createCandidate(candidateRequest)).thenReturn(createdCandidate);

        // Act and Assert
        mockMvc.perform(post("/candidates")
                .content(new ObjectMapper().writeValueAsString(candidateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.sector").value("Logistique"))
                .andExpect(jsonPath("$.numberOfYearsOfExperience").value(3))
                .andExpect(jsonPath("$.levelOfStudies").value("Bac + 2"))
                .andExpect(jsonPath("$.recruitmentDemandId").value(1L));
    }

    @Test
    public void shouldUpdateCandidate() throws Exception {

        // Arrange
        Long candidateId = 1L;
        CandidateRequest candidateRequest = new CandidateRequest();
        candidateRequest.setFirstName("John");
        candidateRequest.setLastName("Doe");
        candidateRequest.setEmail("john.doe@example.com");
        candidateRequest.setPhone("0699515293");
        candidateRequest.setAddress("Marseille, France");
        candidateRequest.setSector("Commerce");
        candidateRequest.setNumberOfYearsOfExperience(5);
        candidateRequest.setLevelOfStudies("Bac + 5");
        candidateRequest.setRecruitmentDemandId(1L);

        CandidateResponse updatedCandidate = new CandidateResponse(candidateId, "John", "Doe", "john.doe@example.com", "0666666666", "Paris, France", "Commerce", 5, "Bac + 5", 1L);

        Mockito.when(candidateService.updateCandidate(candidateId, candidateRequest)).thenReturn(updatedCandidate);

        // Act and Assert
        mockMvc.perform(put("/candidates/{id}", candidateId)
                .content(new ObjectMapper().writeValueAsString(candidateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(candidateId.intValue()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phone").value("0666666666"))
                .andExpect(jsonPath("$.address").value("Paris, France"))
                .andExpect(jsonPath("$.sector").value("Commerce"))
                .andExpect(jsonPath("$.numberOfYearsOfExperience").value(5))
                .andExpect(jsonPath("$.levelOfStudies").value("Bac + 5"))
                .andExpect(jsonPath("$.recruitmentDemandId").value(1));
    }

    @Test
    public void shouldDeleteCandidate() throws Exception {

        // Arrange
        Long candidateId = 1L;

        // Act and Assert
        mockMvc.perform(delete("/candidates/{id}", candidateId))
                .andExpect(status().isNoContent());

        Mockito.verify(candidateService, times(1)).deleteCandidate(candidateId);
    }
}