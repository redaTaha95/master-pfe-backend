package typeValidation.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import typeValidation.application.in.TypeValidationRequest;
import typeValidation.domain.out.TypeValidationRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeValidationService {
    private final TypeValidationRepository typeValidationRepository;

    @Transactional
    public TypeValidationResponse createTypeValidation(TypeValidationRequest typeValidationRequest) {

            TypeValidation typeValidation = TypeValidation.builder()
                    .type(typeValidationRequest.getType())
                    .matricule(typeValidationRequest.getMatricule())
                    .build();

            TypeValidation savedTypeValidation = typeValidationRepository.save(typeValidation);

            return  convertToResponse(savedTypeValidation);

    }

    public List<TypeValidationResponse> getAllTypeValidation() {
        List<TypeValidation> typeValidations = typeValidationRepository.findAll();
        return typeValidations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public TypeValidationResponse getTypeValidationById(Long id) {
        TypeValidation typeValidation = getTypeValidationByIdIfExists(id);
        return convertToResponse(typeValidation);
    }

    public TypeValidationResponse updateTypeValidation(Long id, TypeValidationRequest typeValidationRequest) {

            TypeValidation typeValidation = getTypeValidationByIdIfExists(id);
            updateTypeValidationFromRequest(typeValidation, typeValidationRequest);
            typeValidationRepository.save(typeValidation);
            return convertToResponse(typeValidation);

    }

    public void deleteTypeValidation(Long id) {
        if (!typeValidationRepository.existsById(id)) {
            throw new TypeValidationNotFoundException("TypeValidation not found with id: " + id);
        }
        typeValidationRepository.deleteById(id);
    }


    private void updateTypeValidationFromRequest(TypeValidation typeValidation, TypeValidationRequest typeValidationRequest) {
        typeValidation.setType(typeValidationRequest.getType());
        typeValidation.setMatricule(typeValidationRequest.getMatricule());
    }

    private TypeValidation getTypeValidationByIdIfExists(Long id) {
        return typeValidationRepository.findById(id).orElseThrow(() -> new TypeValidationNotFoundException("TypeValidation not found"));
    }

    private TypeValidationResponse convertToResponse(TypeValidation typeValidation) {
        return TypeValidationResponse.builder()
                .id(typeValidation.getId())
                .type(typeValidation.getType())
                .matricule(typeValidation.getMatricule())
                .build();
    }
}
