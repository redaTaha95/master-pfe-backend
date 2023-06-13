package interview.domain;

import interview.application.in.InterviewRequest;
import interview.application.out.http.candidate.CandidateGateway;
import interview.application.out.http.candidate.CandidateResponse;
import interview.domain.out.IInterviewService;
import interview.domain.out.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewService implements IInterviewService {

    private final InterviewRepository interviewRepository;
    private final CandidateGateway candidateGateway;

    @Override
    public List<InterviewResponse> getAllInterviews() {

        List<Interview> interviews = interviewRepository.findAll();

        return interviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewResponse getInterviewById(Long id) {

        Interview interview = getInterviewByIdIfExists(id);

        return convertToResponse(interview);
    }

    @Override
    public List<InterviewResponse> getInterviewsByCandidateId(Long id) {

        List<Interview> interviews = interviewRepository.findByCandidateId(id);

        return interviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewResponse createInterview(InterviewRequest interviewRequest) {

        CandidateResponse candidate = candidateGateway.getCandidate(interviewRequest.getCandidateId());

        if (candidate != null) {

            Interview interview = buildInterviewFromRequest(interviewRequest);
            Interview savedInterview = interviewRepository.save(interview);

            return convertToResponse(savedInterview);
        }
        else {
            throw new CandidateNotFoundException("Candidate not found");
        }
    }

    @Override
    public InterviewResponse updateInterview(Long id, InterviewRequest interviewRequest) {

        CandidateResponse candidate = candidateGateway.getCandidate(interviewRequest.getCandidateId());

        if (candidate != null) {

            Interview interview = getInterviewByIdIfExists(id);
            updateInterviewFromRequest(interview, interviewRequest);
            interviewRepository.save(interview);

            return convertToResponse(interview);
        }
        else {
            throw new CandidateNotFoundException("Candidate not found");
        }
    }

    @Override
    public void deleteInterview(Long id) {

        if (!interviewRepository.existsById(id)) {
            throw new InterviewNotFoundException("Interview not found with id: " + id);
        }

        interviewRepository.deleteById(id);
    }

    private Interview getInterviewByIdIfExists(Long id) {

        return interviewRepository.findById(id)
                .orElseThrow(() -> new InterviewNotFoundException("Interview not found with id: " + id));
    }

    private Interview buildInterviewFromRequest(InterviewRequest interviewRequest) {

        return Interview.builder()
                .interviewTitle(interviewRequest.getInterviewTitle())
                .interviewDate(interviewRequest.getInterviewDate())
                .candidateId(interviewRequest.getCandidateId())
                .build();
    }

    private void updateInterviewFromRequest(Interview interview, InterviewRequest interviewRequest) {

        interview.setInterviewTitle(interviewRequest.getInterviewTitle());
        interview.setInterviewDate(interviewRequest.getInterviewDate());
        interview.setCandidateId(interviewRequest.getCandidateId());
    }

    private InterviewResponse convertToResponse(Interview interview) {

        return InterviewResponse.builder()
                .id(interview.getId())
                .interviewTitle(interview.getInterviewTitle())
                .interviewDate(interview.getInterviewDate())
                .candidateId(interview.getCandidateId())
                .build();
    }
}
