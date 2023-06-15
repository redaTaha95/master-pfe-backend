package payroll.domain;

public class PayrollNotFoundException extends RuntimeException {
    PayrollNotFoundException(String message) {  super(message);}
}
