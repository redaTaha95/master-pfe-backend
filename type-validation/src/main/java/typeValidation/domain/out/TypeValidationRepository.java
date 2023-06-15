package typeValidation.domain.out;

import org.springframework.data.jpa.repository.JpaRepository;
import typeValidation.domain.TypeValidation;



public interface TypeValidationRepository extends JpaRepository<TypeValidation, Long> {
}