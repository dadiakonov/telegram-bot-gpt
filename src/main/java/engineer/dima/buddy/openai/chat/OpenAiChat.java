package engineer.dima.buddy.openai.chat;

import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import engineer.dima.buddy.openai.OpenAiApiClientException;
import engineer.dima.buddy.openai.OpenAiUnexpectedResponseException;
import engineer.dima.buddy.openai.chat.context.OpenAiChatContextMessage;
import engineer.dima.buddy.openai.chat.context.OpenAiChatContextMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Service class responsible for interacting with the OpenAI Chat API.
 * This class sends messages to the OpenAI Chat API, handles the responses, and
 * manages the interaction context by storing context messages.
 */
@Service
public class OpenAiChat {
    private static final String OPEN_AI_CHAT_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private final OpenAiChatContextMessageRepository openAiChatContextMessageRepository;
    private final OpenAiChatRequestFactory openAiChatRequestFactory;
    private final OpenAiChatHttpRequestFactory openAiChatHttpRequestFactory;
    private final EncodingRegistry encodingRegistry;
    private final RestTemplate restTemplate;
    private final Logger logger;

    /**
     * Constructs a new OpenAiChat service with the given dependencies.
     *
     * @param openAiChatContextMessageRepository The repository to store context messages.
     * @param openAiChatRequestFactory The factory to create OpenAI Chat API requests.
     * @param openAiChatHttpRequestFactory The factory to create HTTP requests for the OpenAI Chat API.
     * @param encodingRegistry The encoding registry used to count tokens in a message.
     * @param restTemplate The RestTemplate used to send HTTP requests.
     */
    @Autowired
    public OpenAiChat(OpenAiChatContextMessageRepository openAiChatContextMessageRepository,
                      OpenAiChatRequestFactory openAiChatRequestFactory,
                      OpenAiChatHttpRequestFactory openAiChatHttpRequestFactory,
                      @Qualifier("openAiChatEncodingRegistryConfiguration") EncodingRegistry encodingRegistry,
                      @Qualifier("openaiRestTemplate") RestTemplate restTemplate) {
        this.openAiChatContextMessageRepository = openAiChatContextMessageRepository;
        this.openAiChatRequestFactory = openAiChatRequestFactory;
        this.openAiChatHttpRequestFactory = openAiChatHttpRequestFactory;
        this.encodingRegistry = encodingRegistry;
        this.restTemplate = restTemplate;
        this.logger = LoggerFactory.getLogger(OpenAiChat.class);
    }

    /**
     * Sends a message to the OpenAI Chat API and returns the response content.
     * This method also manages the interaction context by storing both sent and received messages.
     *
     * @param userId The ID of the user sending the message.
     * @param content The content of the message to be sent.
     * @return The content of the response from the OpenAI Chat API.
     * @throws OpenAiUnexpectedResponseException If the response from OpenAI is unexpected.
     * @throws OpenAiApiClientException If an exception occurs while interacting with the OpenAI API client.
     */
    @Transactional
    public String sendMessage(Long userId, String content) throws OpenAiUnexpectedResponseException, OpenAiApiClientException {
        HttpEntity<OpenAiChatRequest> openAiChatHttpRequest = openAiChatHttpRequestFactory
                .create(openAiChatRequestFactory.create(userId.toString(), content));
        logger.info("Open AI chat HTTP request body: {}", openAiChatHttpRequest.getBody());

        Encoding enc = encodingRegistry.getEncoding(EncodingType.CL100K_BASE);
        openAiChatContextMessageRepository
                .save(new OpenAiChatContextMessage(userId.toString(), OpenAiChatRole.USER, content, enc.countTokens(content)));

        try {
            OpenAiChatResponse openAiChatResponse = restTemplate
                    .postForObject(OPEN_AI_CHAT_ENDPOINT, openAiChatHttpRequest, OpenAiChatResponse.class);

            String responseContent = OpenAiChatResponseExtractor.extractContent(openAiChatResponse);
            logger.info("Open AI chat content response: {}", responseContent);

            openAiChatContextMessageRepository.save(new OpenAiChatContextMessage(userId.toString(), OpenAiChatRole.ASSISTANT,
                    responseContent, OpenAiChatResponseExtractor.extractCompletionTokens(openAiChatResponse)));

            logger.info("Open AI chat returned content: {}", responseContent);
            return responseContent;
        } catch (RestClientException e) {
            logger.warn("Open AI API client exception", e);
            throw new OpenAiApiClientException(e);
        } catch (OpenAiUnexpectedResponseException e) {
            logger.warn(e.getMessage(), e);
            throw e;
        }
    }
}
