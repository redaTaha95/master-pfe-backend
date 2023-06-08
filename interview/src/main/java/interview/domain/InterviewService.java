package interview.domain;

import interview.application.in.InterviewRequest;
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
    public InterviewResponse createInterview(InterviewRequest interviewRequest) {

        Interview interview = buildInterviewFromRequest(interviewRequest);
        Interview savedInterview = interviewRepository.save(interview);

        return convertToResponse(savedInterview);
    }

    @Override
    public InterviewResponse updateInterview(Long id, InterviewRequest interviewRequest) {

        Interview interview = getInterviewByIdIfExists(id);
        updateInterviewFromRequest(interview, interviewRequest);
        interviewRepository.save(interview);

        return convertToResponse(interview);
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
                .build();
    }

    private void updateInterviewFromRequest(Interview interview, InterviewRequest interviewRequest) {

        interview.setInterviewTitle(interviewRequest.getInterviewTitle());
        interview.setInterviewDate(interviewRequest.getInterviewDate());
    }

    private InterviewResponse convertToResponse(Interview interview) {

        return InterviewResponse.builder()
                .id(interview.getId())
                .interviewTitle(interview.getInterviewTitle())
                .interviewDate(interview.getInterviewDate())
                .build();
    }
}
