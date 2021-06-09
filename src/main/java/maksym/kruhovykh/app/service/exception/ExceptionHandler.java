package maksym.kruhovykh.app.service.exception;

import lombok.extern.slf4j.Slf4j;
import maksym.kruhovykh.app.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthException.class)
    public ResponseEntity<ErrorResponse> authException(AuthException exception) {
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = JwtAuthorizationException.class)
    public ResponseEntity<ErrorResponse> jwtAuthorizationException(JwtAuthorizationException exception) {
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = EntityExistsException.class)
    public ResponseEntity<ErrorResponse> entityAlreadyExistsException(EntityExistsException exception) {
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage()),
                HttpStatus.PRECONDITION_FAILED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException exception) {
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(
                createErrorResponse(exception.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    private ErrorResponse createErrorResponse(String errorMessage) {
        return ErrorResponse.builder()
                .message(errorMessage)
                .build();
    }

}
