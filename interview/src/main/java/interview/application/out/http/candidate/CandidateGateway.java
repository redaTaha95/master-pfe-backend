package interview.application.out.http.candidate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CandidateGateway {

    private final CandidateClient candidateClient;

    public CandidateResponse getCandidate(Long candidateId) {

        return candidateClient.getCandidate(candidateId)
                .doOnSuccess(candidateResponse -> log.info("Interview {} created with success", candidateResponse.getId()))
                .doOnError(throwable -> log.error("Error while creating an interview !!"))
                .onErrorResume(throwable -> Mono.empty())
                .block();
    }
}
