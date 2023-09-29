package engineer.dima.buddy.openai.chat;

import engineer.dima.buddy.openai.OpenAiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class OpenAiChatHttpRequestFactory {
    private final OpenAiProperty openAiProperty;

    @Autowired
    public OpenAiChatHttpRequestFactory(OpenAiProperty openAiProperty) {
        this.openAiProperty = openAiProperty;
    }

    public HttpEntity<OpenAiChatRequest> create(OpenAiChatRequest openAiChatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiProperty.getApiKey());

        return new HttpEntity<>(openAiChatRequest, headers);
    }
}
