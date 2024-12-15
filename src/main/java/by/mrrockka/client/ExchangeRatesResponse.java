package by.mrrockka.client;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRatesResponse(Map<String, BigDecimal> rates) {
}
