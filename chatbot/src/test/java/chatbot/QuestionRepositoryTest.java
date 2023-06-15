package chatbot;

import chatbot.model.Question;
import chatbot.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = QuestionRepositoryTest.DataSourceInitializer.class)
public class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.3");

    @Test
    void testSaveQuestion() {
        // Create a test question
        Question question = new Question();
        question.setQuestionText("What is the meaning of life?");
        question.setAnswerText("42");

        // Save the question using the repository
        Question savedQuestion = questionRepository.save(question);

        // Verify the question is saved and has an ID assigned
        assertNotNull(savedQuestion.getId());
    }

    @Test
    void testGetQuestionById() {
        // Create a test question
        Question question = new Question();
        question.setQuestionText("What is the meaning of life?");
        question.setAnswerText("42");

        // Save the question using the repository
        Question savedQuestion = questionRepository.save(question);

        // Retrieve the question by ID
        Question retrievedQuestion = questionRepository.findById(savedQuestion.getId()).orElse(null);

        // Verify that the retrieved question matches the saved question
        assertNotNull(retrievedQuestion);
        assertEquals(question.getQuestionText(), retrievedQuestion.getQuestionText());
        assertEquals(question.getAnswerText(), retrievedQuestion.getAnswerText());
    }

    @Test
    void testUpdateQuestion() {
        // Create a test question
        Question question = new Question();
        question.setQuestionText("What is the meaning of life?");
        question.setAnswerText("42");

        // Save the question using the repository
        Question savedQuestion = questionRepository.save(question);

        // Update the question
        savedQuestion.setQuestionText("What is the meaning of everything?");
        savedQuestion.setAnswerText("The answer is unknown.");

        // Save the updated question
        Question updatedQuestion = questionRepository.save(savedQuestion);

        // Retrieve the updated question by ID
        Question retrievedQuestion = questionRepository.findById(savedQuestion.getId()).orElse(null);

        // Verify that the retrieved question matches the updated question
        assertNotNull(retrievedQuestion);
        assertEquals(updatedQuestion.getQuestionText(), retrievedQuestion.getQuestionText());
        assertEquals(updatedQuestion.getAnswerText(), retrievedQuestion.getAnswerText());
    }

    @Test
    void testDeleteQuestion() {
        // Create a test question
        Question question = new Question();
        question.setQuestionText("What is the meaning of life?");
        question.setAnswerText("42");

        // Save the question using the repository
        Question savedQuestion = questionRepository.save(question);

        // Delete the question
        questionRepository.delete(savedQuestion);

        // Try to retrieve the deleted question by ID
        Question deletedQuestion = questionRepository.findById(savedQuestion.getId()).orElse(null);

        // Verify that the deleted question is null
        assertNull(deletedQuestion);
    }

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
        }
    }
}
