package payslip.domain;

public class PayslipNotFoundException extends RuntimeException {
    public PayslipNotFoundException(String message) {
        super(message);
    }
}
