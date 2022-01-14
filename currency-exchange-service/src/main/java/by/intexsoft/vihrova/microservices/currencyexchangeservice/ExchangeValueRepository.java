package by.intexsoft.vihrova.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<CurrencyExchange, Long> {
    CurrencyExchange findExchangeValueByFromAndTo (String from, String to);
}
