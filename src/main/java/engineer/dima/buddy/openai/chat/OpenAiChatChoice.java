package engineer.dima.buddy.openai.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAiChatChoice(int index, OpenAiChatMessage message, String finishReason) {
}
