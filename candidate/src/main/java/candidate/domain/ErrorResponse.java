package candidate.domain;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private final HttpStatus error;
    private final String message;
}
