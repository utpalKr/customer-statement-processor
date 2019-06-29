package com.rabobank.customer.advice;

import com.rabobank.customer.exception.ValidationException;
import com.rabobank.customer.model.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.UnmarshalException;

@RestControllerAdvice
public class RaboRestControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RaboRestControllerAdvice.class);

    /**
     * Error handler for {@link IllegalArgumentException} being thrown from the backend code.
     * @param request the {@link HttpServletRequest} current request.
     * @param ex the {@link IllegalArgumentException} thrown exception.
     * @return the {@link ResponseEntity} with error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> defaultErrorHandler(final HttpServletRequest request,
                                                            final IllegalArgumentException ex) {

        LOG.error("Invalid input",ex);
        final ErrorMessage error  = ErrorMessage.builder().error(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error handler for {@link Exception} being thrown from backend.
     * @param request the {@link HttpServletRequest} current request.
     * @param ex the {@link Exception} thrown exception.
     * @return the {@link ResponseEntity} with error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> defaultErrorHandler(final HttpServletRequest request,
                                                            final Exception ex) {
        LOG.error("Unhandled Exception caught",ex);
        final ErrorMessage error  = ErrorMessage.builder()
                .error("Unhandled exception caught, please check logs for more details.").build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Error handler for {@link ValidationException} being thrown from the backend code.
     * @param request the {@link HttpServletRequest} current request.
     * @param ex the {@link ValidationException} thrown exception.
     * @return the {@link ResponseEntity} with error message.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> validationExeptionHandler(final HttpServletRequest request,
                                                            final ValidationException ex) {

        LOG.error("Validation exception, kindly check the file format",ex);
        final ErrorMessage error  = ErrorMessage.builder().error(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error handler for {@link UnmarshalException} being thrown from the backend code.
     * @param request the {@link HttpServletRequest} current request.
     * @param ex the {@link ValidationException} thrown exception.
     * @return the {@link ResponseEntity} with error message.
     */
    @ExceptionHandler(UnmarshalException.class)
    public ResponseEntity<ErrorMessage> unmarshalExeptionHandler(final HttpServletRequest request,
                                                                  final UnmarshalException ex) {

        LOG.error("Unmarchal exception, kindly check the file format",ex);
        final ErrorMessage error  = ErrorMessage.builder().error(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
