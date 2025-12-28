package br.com.fiap.service.vehicles.core.domain.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class BusinessException extends RuntimeException {
  private final String code;
  private final String message;
  private final HttpStatus httpStatusCode;

  public BusinessException(ExceptionCode exceptionCode) {
    this.code = exceptionCode.getCode().toString();
    this.message = exceptionCode.getMessage();
    this.httpStatusCode = exceptionCode.getHttpStatus();
  }
}
