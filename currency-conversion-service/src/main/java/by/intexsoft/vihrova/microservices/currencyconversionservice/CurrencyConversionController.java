package by.intexsoft.vihrova.microservices.currencyconversionservice;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CurrencyExchangeServiceProxy proxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConvertion convertCurrency(@PathVariable String from,
                                              @PathVariable String to,
                                              @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConvertion> responseEntity =
                new RestTemplate().getForEntity(
                        "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConvertion.class,
                        uriVariables);

        CurrencyConvertion response = responseEntity.getBody();

        return new CurrencyConvertion(response.getId(), from, to, response.getConversionMultiple(),
                quantity, quantity.multiply(response.getConversionMultiple()), response.getEnvironment() + " rest template");
    }

    //  feign helps us to easily map and call other service with help of proxy-creating

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConvertion convertCurrencyFeign(@PathVariable String from,
                                                   @PathVariable String to,
                                                   @PathVariable BigDecimal quantity) {

        CurrencyConvertion response = proxy.retrieveExchangeValue(from, to);

        logger.info("{}", response);

        return new CurrencyConvertion(response.getId(), from, to, response.getConversionMultiple(),
                quantity, quantity.multiply(response.getConversionMultiple()), response.getEnvironment() + " feign");
    }
}