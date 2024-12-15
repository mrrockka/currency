package by.mrrockka.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class CurrencyServiceTest {

  @Test
  void givenCurrency_whenAttemptToStore_thenShouldStore() {
    final var currency = "GBP";
    final var currencyService = new CurrencyService();
    currencyService.storeCurrency(currency);
    final var actual = currencyService.getAllCurrencies();

    assertThat(actual).hasSize(1);
    assertThat(actual).contains(currency);
  }

  @Test
  void givenCurrency_whenAttemptToGetRates_thenShouldReturnData() {
    final var currency = "GBP";
    final var rates = Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543));
    final var currencyService = new CurrencyService();

    final var actual = currencyService.getCurrencyExchangeRate(currency);
    assertThat(actual).isNotNull();
    assertThat(actual).hasSize(2);
    assertThat(actual).isEqualTo(rates);
  }

}