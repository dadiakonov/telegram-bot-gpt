package engineer.dima.buddy.openai.chat;

import java.util.List;

public record OpenAiChatRequest(String model, List<OpenAiChatMessage> messages, int maxTokens) {
}
