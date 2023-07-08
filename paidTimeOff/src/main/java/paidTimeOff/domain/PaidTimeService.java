package paidTimeOff.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paidTimeOff.application.in.PaidTimeOffRequest;
import paidTimeOff.application.out.http.pto.EmployeeGateway;
import paidTimeOff.application.out.http.pto.EmployeeResponse;
import paidTimeOff.domain.out.PaidTimeOffRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaidTimeService {
    private final PaidTimeOffRepository paidTimeOffRepository;
    private final EmployeeGateway employeeGateway;

    @Transactional
    public PaidTimeOffResponse createPaidTimeOff(PaidTimeOffRequest paidTimeOffRequest) {
        EmployeeResponse employee = employeeGateway.getEmployee(paidTimeOffRequest.getEmployeeId());

        if (employee != null)
        {
            PaidTimeOff paidTimeOff = PaidTimeOff.builder()
                    .details(paidTimeOffRequest.getDetails())
                    .startDate(paidTimeOffRequest.getStartDate())
                    .endDate(paidTimeOffRequest.getEndDate())
                    .employeeId(paidTimeOffRequest.getEmployeeId())
                    .build();

            PaidTimeOff savedPaidTimeOff = paidTimeOffRepository.save(paidTimeOff);

            return  convertToResponse(savedPaidTimeOff,employee);
        }
        else {
            throw new EmployeeNotFoundException("employee not found");
        }
    }
    public List<PaidTimeOffResponse> getAllEmployeesWithLatestPayroll() {
        List<EmployeeResponse> employees = employeeGateway.getAllEmployees();
        List<PaidTimeOffResponse> paidTimeOffResponses = new ArrayList<>();

        employees.forEach(employee -> {
            PaidTimeOff latestPaidTimeOff = paidTimeOffRepository.findFirstByEmployeeIdOrderByStartDateDesc(employee.getId());
            if (latestPaidTimeOff != null) {
                EmployeeResponse employeeResponse = employeeGateway.getEmployee(employee.getId());
                PaidTimeOffResponse paidTimeOffResponse = convertToResponse(latestPaidTimeOff,employeeResponse);
                paidTimeOffResponses.add(paidTimeOffResponse);
            }
        });

        return paidTimeOffResponses;
    }
    public List<PaidTimeOffResponse> getAllPaidTimeOff() {
        List<PaidTimeOff> paidTimeOffs = paidTimeOffRepository.findAll();
        List<PaidTimeOffResponse> paidTimeOffResponses = new ArrayList<>();

        paidTimeOffs.forEach(paidTimeOff -> {
            EmployeeResponse employee = employeeGateway.getEmployee(paidTimeOff.getEmployeeId());
            PaidTimeOffResponse paidTimeOffResponse = convertToResponse(paidTimeOff, employee);
            paidTimeOffResponses.add(paidTimeOffResponse);
        });
        return paidTimeOffResponses;
    }

    public PaidTimeOffResponse getPaidTimeOffById(Long id) {
        PaidTimeOff paidTimeOff = getPaidTimeOffByIdIfExists(id);
        EmployeeResponse employee = employeeGateway.getEmployee(paidTimeOff.getEmployeeId());
        if (employee != null) {
            return convertToResponse(paidTimeOff, employee);
        }
        else {
            throw new EmployeeNotFoundException("employee not found");
        }
    }

    public List<PaidTimeOffResponse>  getPaidTimesOffByEmployeeId(Long id) {
        List<PaidTimeOff> paidTimeOffs = paidTimeOffRepository.findByEmployeeId(id);
        EmployeeResponse employee = employeeGateway.getEmployee(id);

        return paidTimeOffs.stream()
                .map(paidTimeOff -> convertToResponse(paidTimeOff, employee))
                .collect(Collectors.toList());
    }

    public PaidTimeOffResponse updatePaidTimeOff(Long id, PaidTimeOffRequest paidTimeOffRequest) {

        EmployeeResponse employee = employeeGateway.getEmployee(paidTimeOffRequest.getEmployeeId());
        if (employee != null)
        {
            PaidTimeOff paidTimeOff = getPaidTimeOffByIdIfExists(id);
            updatePaidTimeOffFromRequest(paidTimeOff, paidTimeOffRequest);
            paidTimeOffRepository.save(paidTimeOff);
            return convertToResponse(paidTimeOff,employee);
        }
        else {
            throw new EmployeeNotFoundException("Employee not found");
        }

    }

    public void deletePaidTimeOff(Long id) {
        if (!paidTimeOffRepository.existsById(id)) {
            throw new PaidTimeOffNotFoundException("PaidTimeOff not found with id: " + id);
        }
        paidTimeOffRepository.deleteById(id);
    }


    private void updatePaidTimeOffFromRequest(PaidTimeOff paidTimeOff, PaidTimeOffRequest paidTimeOffRequest) {
        paidTimeOff.setDetails(paidTimeOffRequest.getDetails());
        paidTimeOff.setEmployeeId(paidTimeOffRequest.getEmployeeId());
        paidTimeOff.setStartDate(paidTimeOffRequest.getStartDate());
        paidTimeOff.setEndDate(paidTimeOffRequest.getEndDate());
    }

    private PaidTimeOff getPaidTimeOffByIdIfExists(Long id) {
        return paidTimeOffRepository.findById(id).orElseThrow(() -> new PaidTimeOffNotFoundException("PaidTimeOff not found"));
    }

    private PaidTimeOffResponse convertToResponse(PaidTimeOff paidTimeOff,EmployeeResponse employeeResponse) {
        return PaidTimeOffResponse.builder()
                .id(paidTimeOff.getId())
                .details(paidTimeOff.getDetails())
                .startDate(paidTimeOff.getStartDate())
                .endDate(paidTimeOff.getEndDate())
                .employee(employeeResponse)
                .build();
    }
}
