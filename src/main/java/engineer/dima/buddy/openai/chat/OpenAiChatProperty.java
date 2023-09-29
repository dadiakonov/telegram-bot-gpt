package engineer.dima.buddy.openai.chat;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties("open-ai.chat")
public class OpenAiChatProperty {
    private String model;
    private String systemMessage;
    private Integer contextMessagesQty;
    private Integer contextLimitInTokens;
    private Integer responseLimitInTokens;

    public void setModel(String model) {
        this.model = model;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public void setContextMessagesQty(Integer contextMessagesQty) {
        this.contextMessagesQty = contextMessagesQty;
    }

    public void setContextLimitInTokens(Integer contextLimitInTokens) {
        this.contextLimitInTokens = contextLimitInTokens;
    }

    public void setResponseLimitInTokens(Integer responseLimitInTokens) {
        this.responseLimitInTokens = responseLimitInTokens;
    }
}
