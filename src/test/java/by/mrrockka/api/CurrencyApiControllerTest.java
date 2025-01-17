package by.mrrockka.api;

import by.mrrockka.api.currency.CurrencyApiController;
import by.mrrockka.mapper.CurrencyMapper;
import by.mrrockka.service.currency.Currency;
import by.mrrockka.service.currency.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyApiControllerTest {
  @Mock
  private CurrencyService currencyService;
  @Mock
  private CurrencyMapper currencyMapper;
  @InjectMocks
  private CurrencyApiController currencyApiController;

  @Test
  void givenCurrency_whenRequestToStore_shouldStore() {
    final var currency = "AUD";
    currencyApiController.addCurrency(currency);
    verify(currencyService).storeCurrency(currency);
  }

  @Test
  void givenCurrenciesList_whenRequestToGetAll_shouldReturnAll() {
    final var currencies = List.of("AUD", "GBP", "EUR");
    when(currencyService.getAllCurrencyCodes()).thenReturn(currencies);
    assertThat(currencyApiController.getAllCurrencies()
                 .getBody()).isEqualTo(currencies);
  }

  @Test
  void givenCurrency_whenRequestToGetRates_shouldReturnMap() {
    final var currency = Currency.builder()
      .code("EUR")
      .rates(Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543)))
      .build();

    when(currencyService.getCurrencyExchangeRate(currency.code())).thenReturn(currency);
    assertThat(currencyApiController.getExchangeRates(currency.code())
                 .getBody()).isEqualTo(currency.rates());
  }


}