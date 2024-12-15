package by.mrrockka.config;

import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ApplicationConfig {

//  @Bean
  public RestTemplate restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
    return configurer.configure(new RestTemplateBuilder())
      .setConnectTimeout(Duration.ofSeconds(5))
      .setReadTimeout(Duration.ofSeconds(2))
      .build();
  }
}
