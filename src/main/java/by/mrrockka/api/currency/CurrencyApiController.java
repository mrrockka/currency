package by.mrrockka.api.currency;

import by.mrrockka.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyApiController implements CurrencyApi {

  private final CurrencyService currencyService;

  @Override
  public ResponseEntity<Void> addCurrency(String currency) {
    currencyService.storeCurrency(currency);
    return ResponseEntity.status(HttpStatus.CREATED)
      .build();
  }

  @Override
  public ResponseEntity<List<String>> getAllCurrencies() {
    return ResponseEntity.ok(currencyService.getAllCurrencyCodes());
  }

  @Override
  public ResponseEntity<Map<String, BigDecimal>> getExchangeRates(String currencyCode) {
    return ResponseEntity.ok(currencyService.getCurrencyExchangeRate(currencyCode).rates());
  }
}
