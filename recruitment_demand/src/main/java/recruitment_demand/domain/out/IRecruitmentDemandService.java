package recruitment_demand.domain.out;

import recruitment_demand.application.in.RecruitmentDemandRequest;
import recruitment_demand.domain.RecruitmentDemandResponse;

import java.util.List;

public interface IRecruitmentDemandService {

    List<RecruitmentDemandResponse> getAllRecruitmentDemands();
    RecruitmentDemandResponse getRecruitmentDemandById(Long id);
    RecruitmentDemandResponse createRecruitmentDemand(RecruitmentDemandRequest recruitmentDemandRequest);
    RecruitmentDemandResponse updateRecruitmentDemand(Long id, RecruitmentDemandRequest recruitmentDemandRequest);
    void deleteRecruitmentDemand(Long id);
}
