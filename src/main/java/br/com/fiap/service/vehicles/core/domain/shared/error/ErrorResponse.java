package br.com.fiap.service.vehicles.core.domain.shared.error;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  private int code;
  private String message;
  private List<Map<String, Object>> errors;
}
