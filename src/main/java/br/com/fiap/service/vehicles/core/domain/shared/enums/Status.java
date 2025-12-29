package br.com.fiap.service.vehicles.core.domain.shared.enums;

import java.util.Objects;

public enum Status {
  AVAILABLE,
  RESERVED,
  SOLD;

  public static Status compare(Status a, Status b) {
    if (Objects.nonNull(a) && b != a) {
      return a;
    }

    return b;
  }
}
