package typeValidation.application.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import typeValidation.domain.TypeValidationResponse;
import typeValidation.domain.TypeValidationService;

import java.util.List;

@RestController
@RequestMapping("/typeValidations")
@RequiredArgsConstructor
@Validated
public class TypeValidationController {
    private final TypeValidationService typeValidationService;

    @PostMapping
    public ResponseEntity<TypeValidationResponse> createTypeValidation(@RequestBody TypeValidationRequest typeValidationRequest) {
        TypeValidationResponse typeValidationResponse = typeValidationService.createTypeValidation(typeValidationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeValidationResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TypeValidationResponse> getTypeValidationById(@PathVariable @Min(value = 1, message = "Invalid typeValidation ID") Long id) {
        TypeValidationResponse typeValidation = typeValidationService.getTypeValidationById(id);
        return ResponseEntity.ok(typeValidation);
    }

    @GetMapping
    public ResponseEntity<List<TypeValidationResponse>> getAllPaidTimes() {
        List<TypeValidationResponse> typeValidations = typeValidationService.getAllTypeValidation();
        return ResponseEntity.ok(typeValidations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeValidationResponse> updateTypeValidation(@PathVariable @Min(value = 1, message = "Invalid typeValidation ID") Long id, @Valid @RequestBody TypeValidationRequest typeValidationRequest) {
        TypeValidationResponse updatedPTypeValidation = typeValidationService.updateTypeValidation(id, typeValidationRequest);
        return ResponseEntity.ok(updatedPTypeValidation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeValidation(@PathVariable Long id) {
        typeValidationService.deleteTypeValidation(id);
        return ResponseEntity.noContent().build();
    }
}
