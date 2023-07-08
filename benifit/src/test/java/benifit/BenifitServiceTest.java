package benifit;

import benifit.application.in.BenifitRequest;
import benifit.application.out.http.employee.EmployeeGateway;
import benifit.application.out.http.employee.EmployeeResponse;
import benifit.application.out.http.typeValidation.TypeValidationGateway;
import benifit.application.out.http.typeValidation.TypeValidationResponse;
import benifit.domain.Benifit;
import benifit.domain.BenifitResponse;
import benifit.domain.BenifitService;
import benifit.domain.out.BenifitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class BenifitServiceTest {

    @Mock
    private BenifitRepository benifitRepository;
    @Mock
    private EmployeeGateway employeeGateway;
    @Mock
    private TypeValidationGateway typeValidationGateway;

    @InjectMocks
    private BenifitService benifitService;

    @Test
    @DisplayName("should create a benifit")
    public void shouldCreateBenifit() {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        TypeValidationResponse typeValidationResponse = new TypeValidationResponse();
        typeValidationResponse.setMatricule(true);
        Mockito.when(employeeGateway.getEmployee(Mockito.anyLong())).thenReturn(employeeResponse);
        Mockito.when(typeValidationGateway.getTypeValidation(Mockito.anyLong())).thenReturn(typeValidationResponse);

        BenifitRequest benifitRequest = new BenifitRequest();
        benifitRequest.setEmployeeId(1L);
        benifitRequest.setMatricule("AABB3");
        benifitRequest.setTypeValidationId(1L);

        Benifit savedBenifit = new Benifit();
        Mockito.when(benifitRepository.save(Mockito.any(Benifit.class))).thenReturn(savedBenifit);


        BenifitResponse benifitResponse = benifitService.createBenifit(benifitRequest);

        Mockito.verify(benifitRepository).save(Mockito.any(Benifit.class));
        Assertions.assertNotNull(benifitResponse);
    }

    @Test
    @DisplayName("should get all benifits")
    public void shouldGetAllBenifits() {
        List<Benifit> benifits = new ArrayList<>();
        Mockito.when(benifitRepository.findAll()).thenReturn(benifits);

        List<BenifitResponse> benifitResponses = benifitService.getAllBenifits();

        Mockito.verify(benifitRepository).findAll();
        Assertions.assertNotNull(benifitResponses);
    }

    @Test
    @DisplayName("should get benifit by ID")
    public void shouldGetBenifitById() {
        Long benifitId = 1L;
        Optional<Benifit> optionalBenifit = Optional.of(new Benifit(1L,"dddd","AA3SDQ",1L,1L));
        Mockito.when(benifitRepository.findById(benifitId)).thenReturn(optionalBenifit);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        TypeValidationResponse typeValidationResponse = new TypeValidationResponse();
        Mockito.when(employeeGateway.getEmployee(1L)).thenReturn(employeeResponse);
        Mockito.when(typeValidationGateway.getTypeValidation(1L)).thenReturn(typeValidationResponse);

        BenifitResponse benifitResponse = benifitService.getBenifitsById(benifitId);

        Mockito.verify(benifitRepository).findById(benifitId);
        Assertions.assertNotNull(benifitResponse);
    }

    @Test
    @DisplayName("should get benifits by employee ID")
    public void shouldGetBenifitsByEmployeeId() {
        Long employeeId = 1L;
        List<Benifit> benifits = new ArrayList<>();
        Mockito.when(benifitRepository.findByEmployeeId(employeeId)).thenReturn(benifits);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        Mockito.when(employeeGateway.getEmployee(Mockito.anyLong())).thenReturn(employeeResponse);

        List<BenifitResponse> benifitResponses = benifitService.getBenifitsByEmployeeId(employeeId);

        Mockito.verify(benifitRepository).findByEmployeeId(employeeId);
        Assertions.assertNotNull(benifitResponses);
    }

    @Test
    @DisplayName("should update a benifit")
    public void shouldUpdateBenifit() {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        TypeValidationResponse typeValidationResponse = new TypeValidationResponse();
        typeValidationResponse.setMatricule(true);
        Mockito.when(employeeGateway.getEmployee(Mockito.anyLong())).thenReturn(employeeResponse);
        Mockito.when(typeValidationGateway.getTypeValidation(Mockito.anyLong())).thenReturn(typeValidationResponse);

        BenifitRequest benifitRequest = new BenifitRequest();
        benifitRequest.setEmployeeId(1L);
        benifitRequest.setTypeValidationId(1L);
        Long benifitId = 1L;
        Benifit existingBenifit = new Benifit();
        Mockito.when(benifitRepository.findById(benifitId)).thenReturn(Optional.of(existingBenifit));

        BenifitResponse benifitResponse = benifitService.updateBenifit(benifitId, benifitRequest);

        Mockito.verify(benifitRepository).save(existingBenifit);
        Assertions.assertNotNull(benifitResponse);
    }

    @Test
    @DisplayName("should delete a benifit")
    public void shouldDeleteBenifit() {
        Long benifitId = 1L;
        Mockito.when(benifitRepository.existsById(benifitId)).thenReturn(true);

        benifitService.deleteBenifit(benifitId);

        Mockito.verify(benifitRepository).existsById(benifitId);
        Mockito.verify(benifitRepository).deleteById(benifitId);
    }
}