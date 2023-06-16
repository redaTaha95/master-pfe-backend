package candidate.application.out.http.recruitment_demand;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecruitmentDemandGateway {

    private final RecruitmentDemandClient recruitmentDemandClient;

    public RecruitmentDemandResponse getRecruitmentDemand(Long recruitmentDemandId) {

        return recruitmentDemandClient.getRecruitmentDemand(recruitmentDemandId)
                .doOnSuccess(recruitmentDemandResponse -> log.info("Candidate {} created with success", recruitmentDemandResponse.getId()))
                .doOnError(throwable -> log.error("Error while creating a candidate !!"))
                .onErrorResume(throwable -> Mono.empty())
                .block();
    }
}
