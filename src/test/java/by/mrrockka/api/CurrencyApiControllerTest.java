package by.mrrockka.api;

import by.mrrockka.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyApiControllerTest {
  @Mock
  private CurrencyService currencyService;
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
    when(currencyService.getAllCurrencies()).thenReturn(currencies);
    assertThat(currencyApiController.getAllCurrencies().getBody()).isEqualTo(currencies);
  }


}