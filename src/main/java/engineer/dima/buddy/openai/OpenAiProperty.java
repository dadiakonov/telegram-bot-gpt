package engineer.dima.buddy.openai;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties("open-ai")
public class OpenAiProperty {
    private String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
