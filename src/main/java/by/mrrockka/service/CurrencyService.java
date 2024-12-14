package by.mrrockka.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {

  private final List<String> currencies = new ArrayList<>();

  public void storeCurrency(String currency) {
    currencies.add(currency);
  }

  public List<String> getAllCurrencies() {
    return currencies;
  }
}
