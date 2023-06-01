package candidate.domain;

import candidate.application.in.CandidateRequest;
import candidate.domain.out.CandidateRepository;
import candidate.domain.out.ICandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService implements ICandidateService {

    private final CandidateRepository candidateRepository;

    @Override
    public List<CandidateResponse> getAllCandidates() {

        List<Candidate> candidates = candidateRepository.findAll();

        return candidates.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateResponse getCandidateById(Long id) {

        Candidate candidate = getCandidateByIdIfExists(id);

        return convertToResponse(candidate);
    }

    @Override
    public CandidateResponse createCandidate(CandidateRequest candidateRequest) {

        Candidate candidate = buildCandidateFromRequest(candidateRequest);
        Candidate savedCandidate = candidateRepository.save(candidate);

        return convertToResponse(savedCandidate);
    }

    @Override
    public CandidateResponse updateCandidate(Long id, CandidateRequest candidateRequest) {

        Candidate candidate = getCandidateByIdIfExists(id);
        updateCandidateFromRequest(candidate, candidateRequest);
        candidateRepository.save(candidate);

        return convertToResponse(candidate);
    }

    @Override
    public void deleteCandidate(Long id) {

        if (!candidateRepository.existsById(id)) {
            throw new CandidateNotFoundException("Candidate not found with id: " +id);
        }

        candidateRepository.deleteById(id);
    }

    private Candidate getCandidateByIdIfExists(Long id) {

        return candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + id));
    }

    private Candidate buildCandidateFromRequest(CandidateRequest candidateRequest) {

        return Candidate.builder()
                .firstName(candidateRequest.getFirstName())
                .lastName(candidateRequest.getLastName())
                .email(candidateRequest.getEmail())
                .phone(candidateRequest.getPhone())
                .address(candidateRequest.getAddress())
                .build();
    }

    private void updateCandidateFromRequest(Candidate candidate, CandidateRequest candidateRequest) {

        candidate.setFirstName(candidateRequest.getFirstName());
        candidate.setLastName(candidateRequest.getLastName());
        candidate.setEmail(candidateRequest.getEmail());
        candidate.setPhone(candidateRequest.getPhone());
        candidate.setAddress(candidateRequest.getAddress());
    }

    private CandidateResponse convertToResponse(Candidate candidate) {

        return CandidateResponse.builder()
                .id(candidate.getId())
                .firstName(candidate.getFirstName())
                .lastName(candidate.getLastName())
                .email(candidate.getEmail())
                .phone(candidate.getPhone())
                .address(candidate.getAddress())
                .build();
    }
}
