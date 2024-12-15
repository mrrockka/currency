package by.mrrockka.repository.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CurrencyRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final CurrencyRowMapper currencyRowMapper;
  private final ObjectMapper objectMapper;

  @SneakyThrows
  public void insert(String code) {
    final MapSqlParameterSource params = new MapSqlParameterSource()
      .addValue("code", code);
    jdbcTemplate.update("INSERT INTO currency (code) VALUES (:code)", params);
  }

  @SneakyThrows
  public void upsert(String code, Map<String, BigDecimal> rates) {
    final MapSqlParameterSource params = new MapSqlParameterSource()
      .addValue("code", code)
      .addValue("exchange_rates", objectMapper.writeValueAsString(rates));

    jdbcTemplate.update(
      """
        INSERT INTO currency (code, exchange_rates)
        VALUES (:code, :exchange_rates::jsonb)
        ON CONFLICT (code)
        DO UPDATE SET exchange_rates = :exchange_rates::jsonb
        """, params);
  }

  public List<CurrencyEntity> selectAll() {
    return jdbcTemplate.query("SELECT * FROM currency", currencyRowMapper::mapRow);
  }

  public CurrencyEntity select(String code) {
    final MapSqlParameterSource params = new MapSqlParameterSource()
      .addValue("code", code);

    return jdbcTemplate.queryForObject("SELECT * FROM currency WHERE code = :code", params, currencyRowMapper::mapRow);
  }
}
