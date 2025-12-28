package br.com.fiap.service.vehicles.core.domain.shared.exception;

import br.com.fiap.service.vehicles.core.domain.shared.error.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class VehicleExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public final ResponseEntity<ErrorResponse> handle(BusinessException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("code", ex.getCode());
    error.put("message", ex.getMessage());

    return ResponseEntity.status(ex.getHttpStatusCode().value())
        .body(
            ErrorResponse.builder()
                .code(ex.getHttpStatusCode().value())
                .message(ex.getHttpStatusCode().getReasonPhrase())
                .errors(List.of(error))
                .build());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public final ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException ex) {
    var status = HttpStatus.METHOD_NOT_ALLOWED;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public final ResponseEntity<ErrorResponse> handle(HttpMediaTypeNotSupportedException ex) {
    var status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public final ResponseEntity<ErrorResponse> handle(HttpMediaTypeNotAcceptableException ex) {
    var status = HttpStatus.NOT_ACCEPTABLE;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public final ResponseEntity<ErrorResponse> handle(MissingServletRequestParameterException ex) {
    var status = HttpStatus.BAD_REQUEST;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(ServletRequestBindingException.class)
  public final ResponseEntity<ErrorResponse> handle(ServletRequestBindingException ex) {
    var status = HttpStatus.BAD_REQUEST;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(TypeMismatchException.class)
  public final ResponseEntity<ErrorResponse> handle(TypeMismatchException ex) {
    var status = HttpStatus.BAD_REQUEST;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public final ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex) {
    var status = HttpStatus.BAD_REQUEST;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
    var status = HttpStatus.BAD_REQUEST;
    Map<String, Object> errors = new HashMap<>();

    ex.getBindingResult()
        .getAllErrors()
        .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of(errors))
                .build());
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  public final ResponseEntity<ErrorResponse> handle(MissingServletRequestPartException ex) {
    var status = HttpStatus.BAD_REQUEST;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(BindException.class)
  public final ResponseEntity<ErrorResponse> handle(BindException ex) {
    var status = HttpStatus.BAD_REQUEST;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public final ResponseEntity<ErrorResponse> handle(NoHandlerFoundException ex) {
    var status = HttpStatus.NOT_FOUND;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(AsyncRequestTimeoutException.class)
  public final ResponseEntity<ErrorResponse> handle(AsyncRequestTimeoutException ex) {
    var status = HttpStatus.SERVICE_UNAVAILABLE;

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of())
                .build());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity<ErrorResponse> handle(ConstraintViolationException ex) {
    var status = HttpStatus.BAD_REQUEST;
    Map<String, Object> errors = new HashMap<>();

    ex.getConstraintViolations()
        .forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));

    return ResponseEntity.status(status.value())
        .body(
            ErrorResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .errors(List.of(errors))
                .build());
  }

  @ExceptionHandler(ResponseStatusException.class)
  public final ResponseEntity<ErrorResponse> handle(ResponseStatusException ex) {
    return ResponseEntity.status(ex.getStatusCode().value())
        .body(
            ErrorResponse.builder()
                .code(ex.getStatusCode().value())
                .message(ex.getReason())
                .errors(List.of())
                .build());
  }
}
