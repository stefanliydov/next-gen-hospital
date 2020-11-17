package com.company.errors.handlers;

import com.company.constants.Errors;
import com.company.errors.ErrorInfo;
import com.company.errors.exceptions.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UsernameAlreadyTakenException.class})
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleUserAlreadyTakenException(HttpServletRequest req, UsernameAlreadyTakenException ex) {
        return this.buildResponseEntity(new ErrorInfo(HttpStatus.CONFLICT, req.getRequestURL().toString() , ex));
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleBadCredentialsException(HttpServletRequest req){
        return this.buildResponseEntity(new ErrorInfo(HttpStatus.UNAUTHORIZED, req.getRequestURL().toString() , Errors.BAD_CREDENTIALS_ERROR));
    }



    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleUsernameNotFoundException(HttpServletRequest req, UsernameNotFoundException ex){
        return this.buildResponseEntity(new ErrorInfo(HttpStatus.NOT_FOUND, req.getRequestURL().toString() , ex.getMessage()));
    }

    private ResponseEntity<ErrorInfo> buildResponseEntity(ErrorInfo apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
