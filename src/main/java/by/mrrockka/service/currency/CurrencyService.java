package by.mrrockka.service.currency;

import by.mrrockka.mapper.CurrencyMapper;
import by.mrrockka.repository.currency.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyService {

  private final CurrencyRepository currencyRepository;
//  private final CurrencyMapper currencyMapper;
  private final Map<String, Currency> currencies = new HashMap<>();

  @Transactional
  public void storeCurrency(String currencyCode) {
    currencyRepository.insert(currencyCode);
    currencies.put(currencyCode, null);
  }

  @Transactional
  public void updateCurrency(Currency currency) {
    currencyRepository.upsert(currency.code(), currency.rates());
    currencies.put(currency.code(), currency);
  }

  public List<String> getAllCurrencies() {
    return currencies.keySet()
      .stream()
      .toList();
  }

  public Currency getCurrencyExchangeRate(String currencyCode) {
    return currencies.get(currencyCode);
  }
}
