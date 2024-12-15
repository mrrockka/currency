package by.mrrockka.repository.currency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class CurrencyRowMapper {

  private final ObjectMapper objectMapper;

  @SneakyThrows
  CurrencyEntity mapRow(ResultSet rs, int rowNum) {
    final var typeRef = new TypeReference<Map<String, BigDecimal>>() {};
    final var rates = Optional.ofNullable(rs.getString("exchange_rates"))
      .orElse("{}");
    return new CurrencyEntity(rs.getString("code"), objectMapper.readValue(rates, typeRef));
  }
}
