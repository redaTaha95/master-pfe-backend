package interview.domain.out;

import interview.application.in.InterviewRequest;
import interview.domain.InterviewResponse;

import java.util.List;

public interface IInterviewService {

    List<InterviewResponse> getAllInterviews();
    InterviewResponse getInterviewById(Long id);
    InterviewResponse createInterview(InterviewRequest interviewRequest);
    InterviewResponse updateInterview(Long id, InterviewRequest interviewRequest);
    void deleteInterview(Long id);
}
