package by.mrrockka.service;

import by.mrrockka.repository.currency.CurrencyRepository;
import by.mrrockka.service.currency.Currency;
import by.mrrockka.service.currency.CurrencyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

  @Mock
  private CurrencyRepository currencyRepository;
  @InjectMocks
  private CurrencyService currencyService;

  @AfterEach
  void after() {
    verifyNoMoreInteractions(currencyRepository);
  }

  @Test
  void givenCurrency_whenAttemptToStore_thenShouldStore() {
    final var currency = "GBP";
    currencyService.storeCurrency(currency);
    final var actual = currencyService.getAllCurrencies();

    assertThat(actual).hasSize(1);
    assertThat(actual).contains(currency);
    verify(currencyRepository).insert(currency);
  }

  @Test
  void givenCurrency_whenAttemptToGetRates_thenShouldReturnData() {
    final var currency = Currency.builder()
      .code("EUR")
      .rates(Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)))
      .build();
    currencyService.updateCurrency(currency);
    final var actual = currencyService.getCurrencyExchangeRate(currency.code());
    assertThat(actual).isEqualTo(currency);
    verify(currencyRepository).upsert(currency.code(), currency.rates());
  }

}