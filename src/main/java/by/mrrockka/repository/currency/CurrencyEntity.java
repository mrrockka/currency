package by.mrrockka.repository.currency;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Map;

public record CurrencyEntity(@NonNull String code, Map<String, BigDecimal> rates) {}
