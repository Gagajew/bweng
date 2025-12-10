package at.technikum.springrestbackend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice  // Handles exceptions globally for all controllers
public class GlobalExceptionHandler {
    //logger logs error and warning messages
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //Handle Bean Validation errorsy -> 400 Bad Request
    //method handleValidationException responsible for every MethodAgrumentNotValidException-exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        //deals with validation-errors and stores in a map (fieldname -> errormessage)
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream() //lists all field-errors
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage())); //saves errors in map

        LOG.warn("Validation failed: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); //responseEntity with 400 and error map
    }

    //Handle ResourceNotFoundException -> 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        LOG.warn("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    //Handle all other exceptions -> 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        LOG.error("Unexpected error", ex);
        return new ResponseEntity<>(Map.of("error", "Unexpected server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
