package by.mrrockka.mapper;

import by.mrrockka.repository.currency.CurrencyEntity;
import by.mrrockka.service.currency.Currency;
import org.mapstruct.Mapper;

@Mapper
public interface CurrencyMapper {

  Currency toDomain(CurrencyEntity entity);
}
