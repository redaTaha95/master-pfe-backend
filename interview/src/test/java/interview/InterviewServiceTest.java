package interview;

import interview.application.in.InterviewRequest;
import interview.application.out.http.candidate.CandidateGateway;
import interview.application.out.http.candidate.CandidateResponse;
import interview.domain.Interview;
import interview.domain.InterviewResponse;
import interview.domain.InterviewService;
import interview.domain.out.InterviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
public class InterviewServiceTest {

    @Mock
    private InterviewRepository interviewRepository;

    @Mock
    private CandidateGateway candidateGateway;

    @InjectMocks
    private InterviewService interviewService;

    @Test
    @DisplayName("should return all interviews")
    public void shouldReturnAllInterviews() {

        List<Interview> interviews = Arrays.asList(
                new Interview(1L, "Entretien téléphonique", new Date(2023, Calendar.JULY, 9), 1L),
                new Interview(2L, "Entretien technique", new Date(2023, Calendar.SEPTEMBER, 9), 1L)
        );

        Mockito.when(interviewRepository.findAll()).thenReturn(interviews);

        List<InterviewResponse> interviewResponses = interviewService.getAllInterviews();

        Assertions.assertEquals(interviews.size(), interviewResponses.size());
    }

    @Test
    @DisplayName("should return an interview by ID")
    public void shouldReturnAnInterviewById() {

        Long interviewId = 1L;
        Interview interview = new Interview(interviewId, "Entretien téléphonique", new Date(2023, Calendar.JULY, 9), 1L);

        Mockito.when(interviewRepository.findById(interviewId)).thenReturn(Optional.of(interview));

        InterviewResponse interviewResponse = interviewService.getInterviewById(interviewId);

        Assertions.assertEquals(interview.getId(), interviewResponse.getId());
        Assertions.assertEquals(interview.getInterviewTitle(), interviewResponse.getInterviewTitle());
        Assertions.assertEquals(interview.getInterviewDate(), interviewResponse.getInterviewDate());
        Assertions.assertEquals(interview.getCandidateId(), interviewResponse.getCandidateId());
    }

    @Test
    @DisplayName("should save an interview")
    public void shouldSaveAnInterview() {

        InterviewRequest interviewRequest = new InterviewRequest();
        interviewRequest.setInterviewTitle("Entretien RH");
        interviewRequest.setInterviewDate(new Date(2023, Calendar.JUNE, 7));
        interviewRequest.setCandidateId(1L);

        Interview savedInterview = Interview.builder()
                .interviewTitle(interviewRequest.getInterviewTitle())
                .interviewDate(interviewRequest.getInterviewDate())
                .candidateId(interviewRequest.getCandidateId())
                .build();

        Mockito.when(interviewRepository.save(any(Interview.class))).thenReturn(savedInterview);
        Mockito.doReturn(new CandidateResponse())
                .when(candidateGateway)
                .getCandidate(Mockito.anyLong());

        InterviewResponse interview = interviewService.createInterview(interviewRequest);

        Assertions.assertEquals(interview.getInterviewTitle(), interviewRequest.getInterviewTitle());
    }

    @Test
    @DisplayName("should update an interview")
    public void shouldUpdateAnInterview() {

        Long interviewId = 1L;
        InterviewRequest interviewRequest = new InterviewRequest();
        interviewRequest.setInterviewTitle("Entretien final");
        interviewRequest.setInterviewDate(new Date(2023, Calendar.DECEMBER, 8));
        interviewRequest.setCandidateId(1L);

        Interview existingInterview = new Interview(interviewId, "Old Interview Title", new Date(), 1L);

        Mockito.when(interviewRepository.findById(interviewId)).thenReturn(Optional.of(existingInterview));
        Mockito.doReturn(new CandidateResponse())
                .when(candidateGateway)
                .getCandidate(Mockito.anyLong());

        InterviewResponse updatedInterview = interviewService.updateInterview(interviewId, interviewRequest);

        Assertions.assertEquals(existingInterview.getInterviewTitle(), updatedInterview.getInterviewTitle());
        Assertions.assertEquals(existingInterview.getInterviewDate(), updatedInterview.getInterviewDate());
        Assertions.assertEquals(existingInterview.getCandidateId(), updatedInterview.getCandidateId());
    }

    @Test
    @DisplayName("should delete an interview")
    public void shouldDeleteAnInterview() {

        Long interviewId = 1L;

        Mockito.when(interviewRepository.existsById(interviewId)).thenReturn(true);

        interviewService.deleteInterview(interviewId);

        Mockito.verify(interviewRepository).deleteById(interviewId);
    }
}
