package by.intexsoft.vihrova.microservices.currencyexchangeservice;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ExchangeValueRepository repository;

    private final Environment environment;

    @GetMapping(value = "/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

        CurrencyExchange exchangeValue = repository.findExchangeValueByFromAndTo(from, to);

        String port = environment.getProperty("local.server.port");
        exchangeValue.setEnvironment(port);
        logger.info("{}", exchangeValue);

        return exchangeValue;
    }
}
