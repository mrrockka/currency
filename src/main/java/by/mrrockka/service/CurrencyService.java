package by.mrrockka.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {

  private final List<String> currencies = new ArrayList<>();

  public void storeCurrency(String currency) {
    currencies.add(currency);
  }

  public List<String> getAllCurrencies() {
    return currencies;
  }

  public Map<String, BigDecimal> getCurrencyExchangeRate(String currency) {
////    todo: remove mocked data
    return Map.of("AUD", BigDecimal.valueOf(1.123), "GPB", BigDecimal.valueOf(2.543));
  }
}
