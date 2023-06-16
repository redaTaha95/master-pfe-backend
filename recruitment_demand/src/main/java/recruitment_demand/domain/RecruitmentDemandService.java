package recruitment_demand.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import recruitment_demand.application.in.RecruitmentDemandRequest;
import recruitment_demand.domain.out.IRecruitmentDemandService;
import recruitment_demand.domain.out.RecruitmentDemandRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentDemandService implements IRecruitmentDemandService {

    private final RecruitmentDemandRepository recruitmentDemandRepository;

    @Override
    public List<RecruitmentDemandResponse> getAllRecruitmentDemands() {

        List<RecruitmentDemand> recruitmentDemands = recruitmentDemandRepository.findAll();

        return recruitmentDemands.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RecruitmentDemandResponse getRecruitmentDemandById(Long id) {

        RecruitmentDemand recruitmentDemand = getRecruitmentDemandByIdIfExists(id);

        return convertToResponse(recruitmentDemand);
    }

    @Override
    public RecruitmentDemandResponse createRecruitmentDemand(RecruitmentDemandRequest recruitmentDemandRequest) {

        RecruitmentDemand recruitmentDemand = buildRecruitmentDemandFromRequest(recruitmentDemandRequest);
        RecruitmentDemand savedRecruitmentDemand = recruitmentDemandRepository.save(recruitmentDemand);

        return convertToResponse(savedRecruitmentDemand);
    }

    @Override
    public RecruitmentDemandResponse updateRecruitmentDemand(Long id, RecruitmentDemandRequest recruitmentDemandRequest) {

        RecruitmentDemand recruitmentDemand = getRecruitmentDemandByIdIfExists(id);
        updateRecruitmentDemandFromRequest(recruitmentDemand, recruitmentDemandRequest);
        recruitmentDemandRepository.save(recruitmentDemand);

        return convertToResponse(recruitmentDemand);
    }

    @Override
    public void deleteRecruitmentDemand(Long id) {

        if (!recruitmentDemandRepository.existsById(id)) {
            throw new RecruitmentDemandNotFoundException("Recruitment demand not found with id: " +id);
        }

        recruitmentDemandRepository.deleteById(id);
    }

    private RecruitmentDemand getRecruitmentDemandByIdIfExists(Long id) {

        return recruitmentDemandRepository.findById(id)
                .orElseThrow(() -> new RecruitmentDemandNotFoundException("Recruitment demand not found with id: " + id));
    }

    private RecruitmentDemand buildRecruitmentDemandFromRequest(RecruitmentDemandRequest recruitmentDemandRequest) {

        return RecruitmentDemand.builder()
                .postTitle(recruitmentDemandRequest.getPostTitle())
                .postDescription(recruitmentDemandRequest.getPostDescription())
                .department(recruitmentDemandRequest.getDepartment())
                .numberOfProfiles(recruitmentDemandRequest.getNumberOfProfiles())
                .numberOfYearsOfExperience(recruitmentDemandRequest.getNumberOfYearsOfExperience())
                .levelOfStudies(recruitmentDemandRequest.getLevelOfStudies())
                .statusOfDemand(recruitmentDemandRequest.getStatusOfDemand())
                .dateOfDemand(recruitmentDemandRequest.getDateOfDemand())
                .build();
    }

    private void updateRecruitmentDemandFromRequest(RecruitmentDemand recruitmentDemand, RecruitmentDemandRequest recruitmentDemandRequest) {

        recruitmentDemand.setPostTitle(recruitmentDemandRequest.getPostTitle());
        recruitmentDemand.setPostDescription(recruitmentDemandRequest.getPostDescription());
        recruitmentDemand.setDepartment(recruitmentDemandRequest.getDepartment());
        recruitmentDemand.setNumberOfProfiles(recruitmentDemandRequest.getNumberOfProfiles());
        recruitmentDemand.setNumberOfYearsOfExperience(recruitmentDemandRequest.getNumberOfYearsOfExperience());
        recruitmentDemand.setLevelOfStudies(recruitmentDemandRequest.getLevelOfStudies());
        recruitmentDemand.setStatusOfDemand(recruitmentDemandRequest.getStatusOfDemand());
        recruitmentDemand.setDateOfDemand(recruitmentDemandRequest.getDateOfDemand());
    }

    private RecruitmentDemandResponse convertToResponse(RecruitmentDemand recruitmentDemand) {

        return RecruitmentDemandResponse.builder()
                .id(recruitmentDemand.getId())
                .postTitle(recruitmentDemand.getPostTitle())
                .postDescription(recruitmentDemand.getPostDescription())
                .department(recruitmentDemand.getDepartment())
                .numberOfProfiles(recruitmentDemand.getNumberOfProfiles())
                .numberOfYearsOfExperience(recruitmentDemand.getNumberOfYearsOfExperience())
                .levelOfStudies(recruitmentDemand.getLevelOfStudies())
                .statusOfDemand(recruitmentDemand.getStatusOfDemand())
                .dateOfDemand(recruitmentDemand.getDateOfDemand())
                .build();
    }
}
