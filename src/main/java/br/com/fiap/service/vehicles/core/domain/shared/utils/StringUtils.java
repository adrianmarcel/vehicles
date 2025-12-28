package br.com.fiap.service.vehicles.core.domain.shared.utils;

import java.util.Objects;

public class StringUtils {

  public static String compare(String a, String b) {
    if (Objects.nonNull(a) && !b.equalsIgnoreCase(a)) {
      return a;
    }

    return b;
  }
}
