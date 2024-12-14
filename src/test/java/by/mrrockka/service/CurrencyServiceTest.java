package by.mrrockka.service;

import org.junit.jupiter.api.Test;

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

}