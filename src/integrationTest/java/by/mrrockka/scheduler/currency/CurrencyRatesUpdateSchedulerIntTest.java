package by.mrrockka.scheduler.currency;

import by.mrrockka.client.ExchangeRatesClient;
import by.mrrockka.config.PSQLExtension;
import by.mrrockka.service.currency.Currency;
import by.mrrockka.service.currency.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@ExtendWith(PSQLExtension.class)
@SpringBootTest
@TestPropertySource(properties = "scheduler.delay=1000")
class CurrencyRatesUpdateSchedulerIntTest {

  @MockBean
  private ExchangeRatesClient exchangeRatesClient;
  @SpyBean
  private CurrencyService currencyService;

  @Test
  void givenCurrencyAndExchangeRates_whenScheduledExecuted_thenShouldUpdateData() {
    final var currencyCode = "EUR";
    final var rates = Map.of("AUD", BigDecimal.valueOf(1.23), "GBP", BigDecimal.valueOf(1.3133));
    final var currency = Currency.builder()
      .code(currencyCode)
      .rates(rates)
      .build();

    currencyService.storeCurrency(currencyCode);
    verify(currencyService).storeCurrency(currencyCode);
    when(exchangeRatesClient.retrieveExchangeRates(currencyCode)).thenReturn(rates);

    await().atMost(Duration.ofMillis(1100L))
      .untilAsserted(() -> {
        verify(currencyService).getAllCurrencyCodes();
        verify(exchangeRatesClient).retrieveExchangeRates(currency.code());
        verify(currencyService).updateBatch(List.of(currency));
      });
  }

  @Test
  void givenNoCurrencies_whenScheduledExecuted_thenShouldNotUpdateData() {
    await().atMost(Duration.ofMillis(1100L))
      .untilAsserted(() -> {
        verify(currencyService).getAllCurrencyCodes();
        verifyNoInteractions(exchangeRatesClient);
        verifyNoMoreInteractions(currencyService);
      });
  }

}