package com.lakshay.url_shortener.exception;
import com.lakshay.url_shortener.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {
    // handles short code not found
    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ErrorResponse>handleUrlNotFound(UrlNotFoundException ex){
        ErrorResponse error=new ErrorResponse(
                404,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    // handles @Valid validation failures
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>handleValidationFailure(
            MethodArgumentNotValidException ex){
        String message= ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        ErrorResponse error=new ErrorResponse(
                404,
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //handles any other unexpected exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(Exception ex){
        ErrorResponse error=new ErrorResponse(
                500,
                "Something went wrong: "+ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    @ExceptionHandler(UrlExpiredException.class)
    public ResponseEntity<ErrorResponse> handleUrlExpired(Exception ex){
        ErrorResponse error=new ErrorResponse(
                410,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return  ResponseEntity.status(HttpStatus.GONE).body(error);
    }
}
