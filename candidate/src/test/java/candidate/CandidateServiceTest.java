package candidate;

import candidate.application.in.CandidateRequest;
import candidate.application.out.http.recruitment_demand.RecruitmentDemandGateway;
import candidate.application.out.http.recruitment_demand.RecruitmentDemandResponse;
import candidate.domain.Candidate;
import candidate.domain.CandidateResponse;
import candidate.domain.CandidateService;
import candidate.domain.out.CandidateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private RecruitmentDemandGateway recruitmentDemandGateway;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    @DisplayName("should return all candidates")
    public void shouldReturnAllCandidates() {

        List<Candidate> candidates = Arrays.asList(
                new Candidate(1L, "John", "Doe", "john.doe@example.com", "0666666666", "Marseille, France", "RH", 5, "Bac + 5", 1L),
                new Candidate(2L, "Pepsi", "Coca", "pepsi.coca@example.com", "0908070605", "Lyon, France", "Commerce", 2, "Bac + 2", 1L)
        );

        Mockito.when(candidateRepository.findAll()).thenReturn(candidates);

        List<CandidateResponse> candidateResponses = candidateService.getAllCandidates();

        Assertions.assertEquals(candidates.size(), candidateResponses.size());
    }

    @Test
    @DisplayName("should return candidate by ID")
    public void shouldReturnCandidateById() {

        Long candidateId = 1L;
        Candidate candidate = new Candidate(candidateId, "John", "Doe", "john.doe@example.com", "0522232425", "Fes, Maroc", "Commerce", 2, "Bac + 2", 1L);

        Mockito.when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        CandidateResponse candidateResponse = candidateService.getCandidateById(candidateId);

        Assertions.assertEquals(candidate.getId(), candidateResponse.getId());
        Assertions.assertEquals(candidate.getFirstName(), candidateResponse.getFirstName());
        Assertions.assertEquals(candidate.getLastName(), candidateResponse.getLastName());
        Assertions.assertEquals(candidate.getEmail(), candidateResponse.getEmail());
        Assertions.assertEquals(candidate.getPhone(), candidateResponse.getPhone());
        Assertions.assertEquals(candidate.getAddress(), candidateResponse.getAddress());
        Assertions.assertEquals(candidate.getSector(), candidateResponse.getSector());
        Assertions.assertEquals(candidate.getNumberOfYearsOfExperience(), candidateResponse.getNumberOfYearsOfExperience());
        Assertions.assertEquals(candidate.getLevelOfStudies(), candidateResponse.getLevelOfStudies());
        Assertions.assertEquals(candidate.getRecruitmentDemandId(), candidateResponse.getRecruitmentDemandId());
    }

    @Test
    @DisplayName("should save candidate")
    public void shouldSaveCandidate() {

        CandidateRequest candidateRequest = new CandidateRequest();
        candidateRequest.setFirstName("John");
        candidateRequest.setLastName("Cena");
        candidateRequest.setEmail("john.cena@gmail.com");
        candidateRequest.setPhone("0699887766");
        candidateRequest.setAddress("Lisbone, Potugal");
        candidateRequest.setSector("Gestion comptabilité");
        candidateRequest.setNumberOfYearsOfExperience(3);
        candidateRequest.setLevelOfStudies("Bac + 2");
        candidateRequest.setRecruitmentDemandId(1L);

        Candidate savedCandidate = Candidate.builder()
                .firstName(candidateRequest.getFirstName())
                .lastName(candidateRequest.getLastName())
                .email(candidateRequest.getEmail())
                .phone(candidateRequest.getPhone())
                .address(candidateRequest.getAddress())
                .sector(candidateRequest.getSector())
                .numberOfYearsOfExperience(candidateRequest.getNumberOfYearsOfExperience())
                .levelOfStudies(candidateRequest.getLevelOfStudies())
                .recruitmentDemandId(candidateRequest.getRecruitmentDemandId())
                .build();

        Mockito.when(candidateRepository.save(any(Candidate.class))).thenReturn(savedCandidate);
        Mockito.doReturn(new RecruitmentDemandResponse())
                .when(recruitmentDemandGateway)
                .getRecruitmentDemand(Mockito.anyLong());

        CandidateResponse candidate =candidateService.createCandidate(candidateRequest);

        Assertions.assertEquals(candidate.getFirstName(), candidateRequest.getFirstName());
    }

    @Test
    @DisplayName("Should update candidate")
    public void shouldUpdateCandidate() {

        Long candidateId = 1L;
        CandidateRequest candidateRequest = new CandidateRequest();
        candidateRequest.setFirstName("John");
        candidateRequest.setLastName("Doe");
        candidateRequest.setEmail("john.doe@example.com");
        candidateRequest.setPhone("0908070600");
        candidateRequest.setAddress("Safi, Maroc");
        candidateRequest.setSector("Logistique");
        candidateRequest.setNumberOfYearsOfExperience(3);
        candidateRequest.setLevelOfStudies("Bac + 2");
        candidateRequest.setRecruitmentDemandId(1L);

        Candidate existingCandidate = new Candidate(candidateId, "Old First Name", "Old Last Name", "old.email@example.com", "09080706", "Alger, Alger", "Commerce", 2, "Bac + 2", 1L);

        Mockito.when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(existingCandidate));
        Mockito.doReturn(new RecruitmentDemandResponse())
                .when(recruitmentDemandGateway)
                .getRecruitmentDemand(Mockito.anyLong());

        CandidateResponse updatedCandidateResponse = candidateService.updateCandidate(candidateId, candidateRequest);

        Assertions.assertEquals(existingCandidate.getId(), updatedCandidateResponse.getId());
        Assertions.assertEquals(candidateRequest.getFirstName(), updatedCandidateResponse.getFirstName());
        Assertions.assertEquals(candidateRequest.getLastName(), updatedCandidateResponse.getLastName());
        Assertions.assertEquals(candidateRequest.getEmail(), updatedCandidateResponse.getEmail());
        Assertions.assertEquals(candidateRequest.getPhone(), updatedCandidateResponse.getPhone());
        Assertions.assertEquals(candidateRequest.getAddress(), updatedCandidateResponse.getAddress());
        Assertions.assertEquals(candidateRequest.getSector(), updatedCandidateResponse.getSector());
        Assertions.assertEquals(candidateRequest.getNumberOfYearsOfExperience(), updatedCandidateResponse.getNumberOfYearsOfExperience());
        Assertions.assertEquals(candidateRequest.getLevelOfStudies(), updatedCandidateResponse.getLevelOfStudies());
        Assertions.assertEquals(candidateRequest.getRecruitmentDemandId(), updatedCandidateResponse.getRecruitmentDemandId());
    }

    @Test
    @DisplayName("should delete candidate")
    public void shouldDeleteCandidate() {

        Long candidateId = 1L;

        Mockito.when(candidateRepository.existsById(candidateId)).thenReturn(true);

        candidateService.deleteCandidate(candidateId);

        Mockito.verify(candidateRepository).deleteById(candidateId);
    }
}
