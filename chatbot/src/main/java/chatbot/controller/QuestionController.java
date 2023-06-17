package chatbot.controller;

import chatbot.dto.QuestionRequest;
import chatbot.dto.QuestionResponse;
import chatbot.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
@Validated
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> addQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        QuestionResponse questionResponse = questionService.addQuestion(questionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        List<QuestionResponse> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/parents")
    public ResponseEntity<List<QuestionResponse>> getAllParentQuestions() {
        List<QuestionResponse> questions = questionService.getAllParentQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable("id") Long id) {
        QuestionResponse question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionRequest questionRequest) {
        QuestionResponse updatedQuestion = questionService.updateQuestion(id, questionRequest);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
