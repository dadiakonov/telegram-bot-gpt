package engineer.dima.buddy.telegram.bot;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
@ConfigurationProperties("telegram.bot")
public class TelegramBotProperty {
    private String token;
    private String secretToken;
    private List<Long> whitelistedUsersId;

    public void setToken(String token) {
        this.token = token;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public void setWhitelistedUsersId(List<Long> whitelistedUsersId) {
        this.whitelistedUsersId = whitelistedUsersId;
    }
}
