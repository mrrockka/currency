package by.mrrockka.repo.currency;

import by.mrrockka.config.PSQLExtension;
import by.mrrockka.repository.currency.CurrencyEntity;
import by.mrrockka.repository.currency.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PSQLExtension.class)
@SpringBootTest
public class CurrencyRepositoryIntTest {

  @Autowired
  private CurrencyRepository currencyRepository;

  @Test
  void givenCurrencyCode_whenInsert_shouldExistsInDb() {
    final var currencyCode = "GBP";
    final var currency = new CurrencyEntity(currencyCode, Map.of());
    currencyRepository.insert(currencyCode);

    final var actual = currencyRepository.selectAll();
    assertThat(actual).hasSize(1);
    assertThat(actual).contains(currency);
  }

  @Test
  void givenCurrencyCodeAndRates_whenUpsert_shouldExistsInDb() {
    final var currencyCode = "GBP";
    final var rates = Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543));
    final var currency = new CurrencyEntity(currencyCode, rates);
    currencyRepository.upsert(currencyCode, rates);

    final var actual = currencyRepository.select(currencyCode);
    assertThat(actual).isNotNull();
    assertThat(actual).isEqualTo(currency);
  }

  @Test
  void givenCurrencyCodeAndRates_whenCurrencyRatesUpdated_shouldUpdateExistingDataInDb() {
    final var currencyCode = "GBP";
    final var initial = new CurrencyEntity(currencyCode, Map.of());
    final var updated = new CurrencyEntity(currencyCode,
                                           Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)));
    currencyRepository.insert(initial.code());
    var actual = currencyRepository.select(currencyCode);
    assertThat(actual).isNotNull();
    assertThat(actual).isEqualTo(initial);

    currencyRepository.upsert(updated.code(), updated.rates());
    actual = currencyRepository.select(currencyCode);
    assertThat(actual).isNotNull();
    assertThat(actual).isEqualTo(updated);
  }

}
