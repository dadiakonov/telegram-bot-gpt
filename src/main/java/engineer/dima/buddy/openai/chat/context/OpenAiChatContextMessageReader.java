package engineer.dima.buddy.openai.chat.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service responsible for reading OpenAI Chat context messages from the repository.
 */
@Service
public class OpenAiChatContextMessageReader {
    private final OpenAiChatContextMessageRepository openAiChatContextMessageRepository;

    /**
     * Constructor for OpenAiChatContextMessageReader. It initializes the OpenAiChatContextMessageRepository.
     *
     * @param openAiChatContextMessageRepository The repository of OpenAiChatContextMessages.
     */
    @Autowired
    public OpenAiChatContextMessageReader(OpenAiChatContextMessageRepository openAiChatContextMessageRepository) {
        this.openAiChatContextMessageRepository = openAiChatContextMessageRepository;
    }

    /**
     * Finds the last context messages for a given user that are within the specified limits.
     * The limits pertain to the sum of tokens and the quantity of context messages.
     * The messages are retrieved in descending order, and the method ensures that the total token count does not exceed maxSumOfTokens.
     *
     * @param endUser The ID of the end user for which the context messages are being retrieved.
     * @param maxSumOfTokens The maximum allowable sum of tokens for the retrieved context messages.
     * @param maxContextMessageQty The maximum allowable quantity of retrieved context messages.
     * @return A list of OpenAiChatContextMessages that adhere to the specified limits.
     */
    public List<OpenAiChatContextMessage> findLastContextMessagesWithinLimits(String endUser, Integer maxSumOfTokens, Integer maxContextMessageQty) {
        Pageable pageable = PageRequest
                .of(0, maxContextMessageQty, Sort.by(Sort.Direction.DESC, "openAiChatContextMessageId"));

        List<OpenAiChatContextMessage> openAiChatContextMessages = openAiChatContextMessageRepository.findAllByEndUser(endUser, pageable);

        AtomicInteger sum = new AtomicInteger(0);
        return openAiChatContextMessages.stream()
                .filter(contextMessage -> sum.addAndGet(contextMessage.getTokens()) <= maxSumOfTokens)
                .toList();
    }
}
