package by.mrrockka.service.currency;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Builder(toBuilder = true)
public record Currency(@NonNull String code, Map<String, BigDecimal> rates) {

  public Optional<Map<String, BigDecimal>> optRates() {
    return Optional.ofNullable(rates);
  }
}
