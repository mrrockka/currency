package by.mrrockka.scheduler.currency;

import by.mrrockka.client.ExchangeRatesClient;
import by.mrrockka.service.currency.Currency;
import by.mrrockka.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyRatesUpdateScheduler {

  private final CurrencyService currencyService;
  private final ExchangeRatesClient exchangeRatesClient;

  @Scheduled(fixedDelayString = "${scheduler.delay}", initialDelay = 100L)
  public void updateExchangeRates() {
    final var currencies = currencyService.getAllCurrencyCodes();

    if (!currencies.isEmpty()) {
      final var updatedCurrencies = currencies.stream()
        .map(currency -> Currency.builder()
          .code(currency)
          .rates(exchangeRatesClient.retrieveExchangeRates(currency))
          .build())
        .toList();

      currencyService.updateBatch(updatedCurrencies);
    }
  }
}
