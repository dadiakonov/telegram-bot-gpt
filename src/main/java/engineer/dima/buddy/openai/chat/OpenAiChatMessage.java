package engineer.dima.buddy.openai.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAiChatMessage(OpenAiChatRole role, String content) {
}
