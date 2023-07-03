package benifit.domain;

import benifit.application.in.BenifitRequest;
import benifit.application.out.http.employee.EmployeeGateway;
import benifit.application.out.http.employee.EmployeeResponse;
import benifit.application.out.http.typeValidation.TypeValidationGateway;
import benifit.application.out.http.typeValidation.TypeValidationResponse;
import benifit.domain.out.BenifitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BenifitService {
    private final BenifitRepository benifitRepository;
    private final EmployeeGateway employeeGateway;
    private final TypeValidationGateway typeValidationGateway;

    @Transactional
    public BenifitResponse createBenifit(BenifitRequest benifitRequest) {
        EmployeeResponse employee = employeeGateway.getEmployee(benifitRequest.getEmployeeId());
        TypeValidationResponse typeValidationResponse = typeValidationGateway.getTypeValidation(benifitRequest.getTypeValidationId());

        if (employee != null )
        {
            if (typeValidationResponse.getMatricule())
            {
                Benifit benifit = BenifitWithMatricule(benifitRequest);
                Benifit savedBenifit = benifitRepository.save(benifit);

                return  convertToResponse(savedBenifit,employee,typeValidationResponse);
            }
            else if (!typeValidationResponse.getMatricule())
            {
                Benifit benifit = BenifitWithoutMatricule(benifitRequest);
                Benifit savedBenifit = benifitRepository.save(benifit);

                return  convertToResponse(savedBenifit,employee,typeValidationResponse);
            }
            else{
                throw new TypeValidationNotFoundException("type validation not found");
            }

        }

        else {
            throw new EmployeeNotFoundException("employee not found");
        }
    }

    public List<BenifitResponse> getAllBenifits() {

        List<Benifit> benifits = benifitRepository.findAll();
        List<BenifitResponse> benifitResponses = new ArrayList<>();

        benifits.forEach(benifit -> {
            TypeValidationResponse typeValidationResponse = typeValidationGateway.getTypeValidation(benifit.getTypeValidationId());
            EmployeeResponse employee = employeeGateway.getEmployee(benifit.getEmployeeId());
            BenifitResponse benifitResponse = convertToResponse(benifit, employee, typeValidationResponse);
            benifitResponses.add(benifitResponse);
        });
        return benifitResponses;
    }

    public BenifitResponse getBenifitsById(Long id) {
        Benifit benifit = getBenifitByIdIfExists(id);
        EmployeeResponse employee = employeeGateway.getEmployee(id);
        TypeValidationResponse typeValidationResponse = typeValidationGateway.getTypeValidation(id);

        return convertToResponse(benifit,employee,typeValidationResponse);
    }

    public List<BenifitResponse>  getBenifitsByEmployeeId(Long id) {

        List<Benifit> benifits = benifitRepository.findByEmployeeId(id);
        EmployeeResponse employee = employeeGateway.getEmployee(id);

        return benifits.stream()
                .map(benifit -> convertToResponse(benifit, employee,getTypeValidationByBenifitId(benifit.getTypeValidationId())))
                .collect(Collectors.toList());
    }

    public BenifitResponse updateBenifit(Long id, BenifitRequest benifitRequest) {

        EmployeeResponse employee = employeeGateway.getEmployee(benifitRequest.getEmployeeId());
        TypeValidationResponse typeValidationResponse = typeValidationGateway.getTypeValidation(benifitRequest.getTypeValidationId());

        if (employee != null)
        {
            Benifit benifit = getBenifitByIdIfExists(id);
            if (typeValidationResponse.getMatricule())
            {
                updateBenifitFromRequestWithMatricule(benifit, benifitRequest);
            }
            else if (!typeValidationResponse.getMatricule())
            {
                updateBenifitFromRequestWithoutMatricule(benifit, benifitRequest);
            }
            else
            {
                throw new TypeValidationNotFoundException("type validation not found");
            }
            benifitRepository.save(benifit);
            return convertToResponse(benifit, employee,typeValidationResponse);

        }
        else {
            throw new EmployeeNotFoundException("Employee not found");
        }

    }

    public void deleteBenifit(Long id) {
        if (!benifitRepository.existsById(id)) {
            throw new BenifitNotFoundException("Benifit not found with id: " + id);
        }
        benifitRepository.deleteById(id);
    }


    private void updateBenifitFromRequestWithMatricule(Benifit benifit, BenifitRequest benifitRequest) {

        benifit.setMatricule(benifitRequest.getMatricule());
        benifit.setDetails(benifitRequest.getDetails());
        benifit.setEmployeeId(benifitRequest.getEmployeeId());
        benifit.setTypeValidationId(benifitRequest.getTypeValidationId());

    }

    private void updateBenifitFromRequestWithoutMatricule(Benifit benifit, BenifitRequest benifitRequest) {

        benifit.setDetails(benifitRequest.getDetails());
        benifit.setEmployeeId(benifitRequest.getEmployeeId());
        benifit.setTypeValidationId(benifitRequest.getTypeValidationId());

    }

    private Benifit getBenifitByIdIfExists(Long id) {
        return benifitRepository.findById(id).orElseThrow(() -> new BenifitNotFoundException("Benifit not found"));
    }

    private TypeValidationResponse getTypeValidationByBenifitId(Long id) {
        return typeValidationGateway.getTypeValidation(id);
    }


    private Benifit BenifitWithMatricule(BenifitRequest benifitRequest) {
        return Benifit.builder()
                .details(benifitRequest.getDetails())
                .matricule(benifitRequest.getMatricule())
                .employeeId(benifitRequest.getEmployeeId())
                .typeValidationId(benifitRequest.getTypeValidationId())
                .build();
    }

    private Benifit BenifitWithoutMatricule(BenifitRequest benifitRequest) {
        return Benifit.builder()
                .details(benifitRequest.getDetails())
                .employeeId(benifitRequest.getEmployeeId())
                .typeValidationId(benifitRequest.getTypeValidationId())
                .build();
    }

    private BenifitResponse convertToResponse(Benifit benifit, EmployeeResponse employeeResponse,TypeValidationResponse typeValidationResponse) {

        return BenifitResponse.builder()
                .id(benifit.getId())
                .details(benifit.getDetails())
                .matricule(benifit.getMatricule())
                .typeValidation(typeValidationResponse)
                .employee(employeeResponse)
                .build();
    }
}
