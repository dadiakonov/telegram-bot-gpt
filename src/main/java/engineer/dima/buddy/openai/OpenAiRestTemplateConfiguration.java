package engineer.dima.buddy.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OpenAiRestTemplateConfiguration {
    private static final int CONNECTION_TIMEOUT_IN_MILLIS = 1000;
    private static final int READ_TIMEOUT_IN_MILLIS = 60000;

    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate() {
        ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        return new RestTemplateBuilder()
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .setConnectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT_IN_MILLIS))
                .setReadTimeout(Duration.ofMillis(READ_TIMEOUT_IN_MILLIS))
                .build();
    }
}
