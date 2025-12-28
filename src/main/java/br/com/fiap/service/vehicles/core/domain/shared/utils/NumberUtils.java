package br.com.fiap.service.vehicles.core.domain.shared.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class NumberUtils {

  public static BigDecimal compareBigDecimal(BigDecimal a, BigDecimal b) {
    if (Objects.nonNull(a) && !(b.compareTo(a) == 0)) {
      return a;
    }

    return b;
  }

  public static Integer compareInteger(Integer a, Integer b) {
    if (Objects.nonNull(a) && (a > b || a < b)) {
      return a;
    }

    return b;
  }
}
