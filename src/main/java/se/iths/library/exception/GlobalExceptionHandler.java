package se.iths.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandling(NotFoundException exception, WebRequest request){
        String errorMessageDesc = exception.getLocalizedMessage();
        if (errorMessageDesc == null) errorMessageDesc=exception.toString();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), errorMessageDesc, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    //Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> unauthorizedExceptionHandling(UnauthorizedException exception, WebRequest request){
        String errorMessageDesc = exception.getLocalizedMessage();
        if (errorMessageDesc == null) errorMessageDesc=exception.toString();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), errorMessageDesc, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request){
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }*/

}
