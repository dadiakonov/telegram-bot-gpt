package engineer.dima.buddy.openai.chat;

import engineer.dima.buddy.message.MessageProcessingStrategy;
import engineer.dima.buddy.message.formatter.MessageContentFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation of {@link MessageProcessingStrategy} for processing messages
 * using OpenAI Chat. This service is active when the "openAiChat" profile is active.
 * It delegates the message sending to an instance of {@link OpenAiChat} and applies a
 * series of {@link MessageContentFormatter}s to format the content of the returned message.
 */
@Service
@Profile("openAiChat")
public class OpenAiChatMessageProcessingStrategy implements MessageProcessingStrategy {
    private final OpenAiChat openAiChat;
    private final List<MessageContentFormatter> messageContentFormatters;

    /**
     * Constructs a new instance of {@link OpenAiChatMessageProcessingStrategy}.
     *
     * @param openAiChat                 The {@link OpenAiChat} instance used for sending messages.
     * @param messageContentFormatters   A list of {@link MessageContentFormatter} to format the returned message content.
     */
    @Autowired
    public OpenAiChatMessageProcessingStrategy(OpenAiChat openAiChat, List<MessageContentFormatter> messageContentFormatters) {
        this.openAiChat = openAiChat;
        this.messageContentFormatters = messageContentFormatters;
    }

    /**
     * Processes the input message by sending it through {@link OpenAiChat} and applying
     * the {@link MessageContentFormatter}s to the returned message content.
     *
     * @param userId  The ID of the user sending the message.
     * @param message The content of the message to be processed.
     * @return The formatted response message.
     */
    @Override
    public String processMessage(Long userId, String message) {
        String returnedMessage = openAiChat.sendMessage(userId, message);

        return messageContentFormatters.stream()
                .reduce(returnedMessage, (content, formatter) -> formatter.format(content), (prevContent, currentContent) -> currentContent);
    }
}
