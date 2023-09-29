package engineer.dima.buddy.openai.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAiChatResponse(List<OpenAiChatChoice> choices, OpenAiChatUsage usage) {
}
