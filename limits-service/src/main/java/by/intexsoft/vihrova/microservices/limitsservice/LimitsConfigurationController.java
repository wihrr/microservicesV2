package by.intexsoft.vihrova.microservices.limitsservice;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitsConfigurationController {

    private final Configuration configuration;

    @GetMapping("/limits")
    public Limits retrieveLimitsFromConfigurations(){
        return new Limits(configuration.getMax(), configuration.getMin());
    }

//    @GetMapping("/fault/tolerance-example")
//    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")  //specify default method, that comes in, if this one will throw an Exception
//    public LimitConfiguration retrieveConfiguration(){
//        throw new RuntimeException("Not available");
//    }
//
//    public LimitConfiguration fallbackRetrieveConfiguration(){
//        return new LimitConfiguration(999, 9);
//    }
}
