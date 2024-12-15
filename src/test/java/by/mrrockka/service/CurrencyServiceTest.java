package by.mrrockka.service;

import by.mrrockka.client.ExchangeRatesClient;
import by.mrrockka.mapper.CurrencyMapper;
import by.mrrockka.repository.currency.CurrencyEntity;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

  @Mock
  private CurrencyRepository currencyRepository;
  @Mock
  private CurrencyMapper currencyMapper;
  @Mock
  private ExchangeRatesClient exchangeRatesClient;
  @InjectMocks
  private CurrencyService currencyService;

  @Test
  void givenCurrency_whenAttemptToStore_thenShouldStore() {
    final var currency = Currency.builder()
      .code("GBP")
      .build();

    currencyService.storeCurrency(currency.code());

    final var entity =
      new CurrencyEntity("EUR", Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)));
    when(currencyRepository.selectAll()).thenReturn(List.of(entity));
    when(currencyMapper.toDomain(entity)).thenReturn(currency);

    final var actual = currencyService.getAllCurrencyCodes();
    assertThat(actual).hasSize(1);
    assertThat(actual).contains(currency.code());
    verify(currencyRepository).insert(currency.code());
  }

  @Test
  void givenCurrencyCodeAndRates_whenAttemptToGetRates_thenShouldReturnData() {
    final var currency = Currency.builder()
      .code("EUR")
      .rates(Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)))
      .build();
    final var entity =
      new CurrencyEntity("EUR", Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)));
    when(currencyRepository.select(currency.code())).thenReturn(entity);
    when(currencyMapper.toDomain(entity)).thenReturn(currency);
    currencyService.updateCurrency(currency);
    final var actual = currencyService.getCurrencyExchangeRate(currency.code());
    assertThat(actual).isEqualTo(currency);
    verify(currencyRepository).upsert(currency.code(), currency.rates());
  }

  @Test
  void givenCurrencyCodeAndNoRates_whenAttemptToGetRates_thenShouldPopulateDataAndReturn() {
    final var expected = Currency.builder()
      .code("EUR")
      .rates(Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)))
      .build();

    final var entity =
      new CurrencyEntity("EUR", Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)));
    when(currencyRepository.select(expected.code())).thenReturn(entity);
    when(currencyMapper.toDomain(entity)).thenReturn(expected);
    currencyService.storeCurrency(expected.code());
    when(exchangeRatesClient.retrieveExchangeRates(expected.code())).thenReturn(expected.rates());
    final var actual = currencyService.getCurrencyExchangeRate(expected.code());
    assertThat(actual).isEqualTo(expected);
    verify(currencyRepository).insert(expected.code());
    verify(currencyRepository).upsert(expected.code(), expected.rates());
  }

}