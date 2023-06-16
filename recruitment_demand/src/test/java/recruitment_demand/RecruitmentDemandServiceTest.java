package recruitment_demand;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import recruitment_demand.application.in.RecruitmentDemandRequest;
import recruitment_demand.domain.RecruitmentDemand;
import recruitment_demand.domain.RecruitmentDemandResponse;
import recruitment_demand.domain.RecruitmentDemandService;
import recruitment_demand.domain.out.RecruitmentDemandRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RecruitmentDemandServiceTest {

    @Mock
    private RecruitmentDemandRepository recruitmentDemandRepository;

    @InjectMocks
    private RecruitmentDemandService recruitmentDemandService;

    @Test
    @DisplayName("should return all recruitment demand")
    public void shouldReturnAllRecruitmentDemand() {

        List<RecruitmentDemand> recruitmentDemands = Arrays.asList(
                new RecruitmentDemand(1L, "Software Developer", "Post Description", "IT", 5, 2, "Bac + 5", "En cours", new Date()),
                new RecruitmentDemand(2L, "Software Engineer", "Post Description", "IT", 1, 2, "Bac + 2", "En cours", new Date())
        );

        Mockito.when(recruitmentDemandRepository.findAll()).thenReturn(recruitmentDemands);

        List<RecruitmentDemandResponse> recruitmentDemandResponses = recruitmentDemandService.getAllRecruitmentDemands();

        Assertions.assertEquals(recruitmentDemands.size(), recruitmentDemandResponses.size());
    }

    @Test
    @DisplayName("should return a recruitment demand by ID")
    public void shouldReturnRecruitmentDemandById() {

        Long recruitmentDemandId = 1L;
        RecruitmentDemand recruitmentDemand = new RecruitmentDemand(recruitmentDemandId, "Software Developer", "Post Description", "IT", 5, 2, "Bac + 5", "En cours", new Date());

        Mockito.when(recruitmentDemandRepository.findById(recruitmentDemandId)).thenReturn(Optional.of(recruitmentDemand));

        RecruitmentDemandResponse recruitmentDemandResponse = recruitmentDemandService.getRecruitmentDemandById(recruitmentDemandId);

        Assertions.assertEquals(recruitmentDemand.getId(), recruitmentDemandResponse.getId());
        Assertions.assertEquals(recruitmentDemand.getPostTitle(), recruitmentDemandResponse.getPostTitle());
        Assertions.assertEquals(recruitmentDemand.getPostDescription(), recruitmentDemandResponse.getPostDescription());
        Assertions.assertEquals(recruitmentDemand.getDepartment(), recruitmentDemandResponse.getDepartment());
        Assertions.assertEquals(recruitmentDemand.getNumberOfProfiles(), recruitmentDemandResponse.getNumberOfProfiles());
        Assertions.assertEquals(recruitmentDemand.getNumberOfYearsOfExperience(), recruitmentDemandResponse.getNumberOfYearsOfExperience());
        Assertions.assertEquals(recruitmentDemand.getLevelOfStudies(), recruitmentDemandResponse.getLevelOfStudies());
        Assertions.assertEquals(recruitmentDemand.getStatusOfDemand(), recruitmentDemandResponse.getStatusOfDemand());
        Assertions.assertEquals(recruitmentDemand.getDateOfDemand(), recruitmentDemandResponse.getDateOfDemand());

    }

    @Test
    @DisplayName("should save recruitment demand")
    public void shouldSaveRecruitmentDemand() {

        RecruitmentDemandRequest recruitmentDemandRequest = new RecruitmentDemandRequest();

        recruitmentDemandRequest.setPostTitle("Post Title");
        recruitmentDemandRequest.setPostDescription("Post Description");
        recruitmentDemandRequest.setDepartment("IT");
        recruitmentDemandRequest.setNumberOfProfiles(3);
        recruitmentDemandRequest.setNumberOfYearsOfExperience(4);
        recruitmentDemandRequest.setLevelOfStudies("Bac + 4");
        recruitmentDemandRequest.setStatusOfDemand("Annulé");
        recruitmentDemandRequest.setDateOfDemand(new Date());

        RecruitmentDemand savedRecruitmentDemand = RecruitmentDemand.builder()
                .postTitle(recruitmentDemandRequest.getPostTitle())
                .postDescription(recruitmentDemandRequest.getPostDescription())
                .department(recruitmentDemandRequest.getDepartment())
                .numberOfProfiles(recruitmentDemandRequest.getNumberOfProfiles())
                .numberOfYearsOfExperience(recruitmentDemandRequest.getNumberOfYearsOfExperience())
                .levelOfStudies(recruitmentDemandRequest.getLevelOfStudies())
                .statusOfDemand(recruitmentDemandRequest.getStatusOfDemand())
                .dateOfDemand(recruitmentDemandRequest.getDateOfDemand())
                .build();

        Mockito.when(recruitmentDemandRepository.save(any(RecruitmentDemand.class))).thenReturn(savedRecruitmentDemand);

        RecruitmentDemandResponse recruitmentDemand =recruitmentDemandService.createRecruitmentDemand(recruitmentDemandRequest);

        Assertions.assertEquals(recruitmentDemand.getNumberOfProfiles(), recruitmentDemandRequest.getNumberOfProfiles());
    }

    @Test
    @DisplayName("should update recruitment demand")
    public void shouldUpdateRecruitmentDemand() {

        Long recruitmentDemandId = 1L;
        RecruitmentDemandRequest recruitmentDemandRequest = new RecruitmentDemandRequest();

        recruitmentDemandRequest.setPostTitle("Title");
        recruitmentDemandRequest.setPostDescription("Description");
        recruitmentDemandRequest.setDepartment("IT");
        recruitmentDemandRequest.setNumberOfProfiles(3);
        recruitmentDemandRequest.setNumberOfYearsOfExperience(4);
        recruitmentDemandRequest.setLevelOfStudies("Bac + 4");
        recruitmentDemandRequest.setStatusOfDemand("Annulé");
        recruitmentDemandRequest.setDateOfDemand(new Date());

        RecruitmentDemand existingRecruitmentDemand = new RecruitmentDemand(recruitmentDemandId, "Old Title", "Old Description", "IT", 5, 2, "Bac + 5", "En cours", new Date());

        Mockito.when(recruitmentDemandRepository.findById(recruitmentDemandId)).thenReturn(Optional.of(existingRecruitmentDemand));

        RecruitmentDemandResponse updatedRecruitmentDemandResponse = recruitmentDemandService.updateRecruitmentDemand(recruitmentDemandId, recruitmentDemandRequest);

        Assertions.assertEquals(existingRecruitmentDemand.getId(), updatedRecruitmentDemandResponse.getId());
        Assertions.assertEquals(existingRecruitmentDemand.getPostTitle(), updatedRecruitmentDemandResponse.getPostTitle());
        Assertions.assertEquals(existingRecruitmentDemand.getPostDescription(), updatedRecruitmentDemandResponse.getPostDescription());
        Assertions.assertEquals(existingRecruitmentDemand.getDepartment(), updatedRecruitmentDemandResponse.getDepartment());
        Assertions.assertEquals(existingRecruitmentDemand.getNumberOfProfiles(), updatedRecruitmentDemandResponse.getNumberOfProfiles());
        Assertions.assertEquals(existingRecruitmentDemand.getNumberOfYearsOfExperience(), updatedRecruitmentDemandResponse.getNumberOfYearsOfExperience());
        Assertions.assertEquals(existingRecruitmentDemand.getLevelOfStudies(), updatedRecruitmentDemandResponse.getLevelOfStudies());
        Assertions.assertEquals(existingRecruitmentDemand.getStatusOfDemand(), updatedRecruitmentDemandResponse.getStatusOfDemand());
        Assertions.assertEquals(existingRecruitmentDemand.getDateOfDemand(), updatedRecruitmentDemandResponse.getDateOfDemand());

    }

    @Test
    @DisplayName("should delete recruitment demand")
    public void shouldDeleteRecruitmentDemand() {

        Long recruitmentDemandId = 1L;
        Mockito.when(recruitmentDemandRepository.existsById(recruitmentDemandId)).thenReturn(true);

        recruitmentDemandService.deleteRecruitmentDemand(recruitmentDemandId);

        Mockito.verify(recruitmentDemandRepository).deleteById(recruitmentDemandId);
    }
}