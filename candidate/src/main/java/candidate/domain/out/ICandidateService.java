package candidate.domain.out;

import candidate.application.in.CandidateRequest;
import candidate.domain.CandidateResponse;

import java.util.List;

public interface ICandidateService {

    List<CandidateResponse> getAllCandidates();
    CandidateResponse getCandidateById(Long id);
    List<CandidateResponse> getCandidatesByRecruitmentDemandId(Long id);
    CandidateResponse createCandidate(CandidateRequest candidateRequest);
    CandidateResponse updateCandidate(Long id, CandidateRequest candidateRequest);
    void deleteCandidate(Long id);
}
