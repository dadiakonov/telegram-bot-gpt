package engineer.dima.buddy.openai.chat;

import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import engineer.dima.buddy.openai.chat.context.OpenAiChatContextMessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for creating OpenAiChatRequest objects to be sent to the OpenAI Chat API.
 * It builds the request by incorporating system messages, user messages, and the context of previous
 * interactions, respecting token limits set in the properties.
 */
@Service
public class OpenAiChatRequestFactory {
    private final OpenAiChatProperty openAiChatProperty;
    private final OpenAiChatContextMessageReader openAiChatContextMessageReader;
    private final EncodingRegistry encodingRegistry;

    /**
     * Constructs a new OpenAiChatRequestFactory with the given dependencies.
     *
     * @param openAiChatProperty The properties containing settings for the OpenAI Chat API.
     * @param openAiChatContextMessageReader The reader used to retrieve the context messages for the request.
     * @param encodingRegistry The encoding registry used to count tokens in a message.
     */
    @Autowired
    public OpenAiChatRequestFactory(OpenAiChatProperty openAiChatProperty, OpenAiChatContextMessageReader openAiChatContextMessageReader,
                                    EncodingRegistry encodingRegistry) {
        this.openAiChatProperty = openAiChatProperty;
        this.openAiChatContextMessageReader = openAiChatContextMessageReader;
        this.encodingRegistry = encodingRegistry;
    }

    /**
     * Creates an OpenAiChatRequest object by including a predefined system message, user content,
     * and context messages within the token limits specified in the OpenAiChatProperty.
     * The method loads the latest messages to ensure that GPT understands the communication context.
     * The limits are adhered to in order to avoid overloading the requests.
     *
     * @param endUser The ID of the end user for which the request is being created.
     * @param content The content of the message from the user.
     * @return A newly created OpenAiChatRequest object ready to be sent to the OpenAI Chat API.
     */
    public OpenAiChatRequest create(String endUser, String content) {
        List<OpenAiChatMessage> openAiChatMessages = new ArrayList<>();

        String systemMessage = openAiChatProperty.getSystemMessage();
        openAiChatMessages.add(new OpenAiChatMessage(OpenAiChatRole.SYSTEM, systemMessage));

        Encoding enc = encodingRegistry.getEncoding(EncodingType.CL100K_BASE);
        Integer contextLimitInTokens = openAiChatProperty.getContextLimitInTokens() - enc.countTokens(systemMessage);

        openAiChatContextMessageReader
                .findLastContextMessagesWithinLimits(endUser, contextLimitInTokens, openAiChatProperty.getContextMessagesQty())
                .stream()
                .map(ctxMsg -> new OpenAiChatMessage(ctxMsg.getRole(), ctxMsg.getContent()))
                .forEachOrdered(openAiChatMessages::add);

        openAiChatMessages.add(new OpenAiChatMessage(OpenAiChatRole.USER, content));

        return new OpenAiChatRequest(openAiChatProperty.getModel(), openAiChatMessages, openAiChatProperty.getResponseLimitInTokens());
    }
}
