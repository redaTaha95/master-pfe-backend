package paidTimeOff.domain;

public class PaidTimeOffNotFoundException extends RuntimeException {
    PaidTimeOffNotFoundException(String message) {  super(message);}
}
