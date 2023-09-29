package engineer.dima.buddy.openai.chat.context;

import engineer.dima.buddy.openai.chat.OpenAiChatRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAiChatContextMessageReaderTest {
    private static final String END_USER_1 = "1";
    private static final String END_USER_2 = "2";
    private static final String CONTENT = "some content";

    private final OpenAiChatContextMessageRepository openAiChatContextMessageRepository;
    private final OpenAiChatContextMessageReader openAiChatContextMessageReader;

    @Autowired
    public OpenAiChatContextMessageReaderTest(OpenAiChatContextMessageRepository openAiChatContextMessageRepository,
                                              OpenAiChatContextMessageReader openAiChatContextMessageReader) {
        this.openAiChatContextMessageRepository = openAiChatContextMessageRepository;
        this.openAiChatContextMessageReader = openAiChatContextMessageReader;
    }

    @Test
    public void testFindLastContextMessagesWithinLimitsReturnsEmptyListBecauseOfTokenLimit() {
        OpenAiChatContextMessage contextMessage1 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.USER, CONTENT, 10);
        openAiChatContextMessageRepository.save(contextMessage1);
        OpenAiChatContextMessage contextMessage2 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.USER, CONTENT, 100);
        openAiChatContextMessageRepository.save(contextMessage2);
        OpenAiChatContextMessage contextMessage3 = new OpenAiChatContextMessage(END_USER_2, OpenAiChatRole.USER, CONTENT, 10);
        openAiChatContextMessageRepository.save(contextMessage3);

        List<OpenAiChatContextMessage> openAiChatContextMessages = openAiChatContextMessageReader
                .findLastContextMessagesWithinLimits(END_USER_1, 50, 5);

        assertTrue(openAiChatContextMessages.isEmpty());
    }

    @Test
    public void testFindLastContextMessagesWithinLimitsReturnsLimitedListBecauseOfQtyLimit() {
        OpenAiChatContextMessage contextMessage1 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.USER, CONTENT, 10);
        openAiChatContextMessageRepository.save(contextMessage1);
        OpenAiChatContextMessage contextMessage2 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.USER, CONTENT, 100);
        openAiChatContextMessageRepository.save(contextMessage2);
        OpenAiChatContextMessage contextMessage3 = new OpenAiChatContextMessage(END_USER_2, OpenAiChatRole.USER, CONTENT, 10);
        openAiChatContextMessageRepository.save(contextMessage3);

        List<OpenAiChatContextMessage> openAiChatContextMessages = openAiChatContextMessageReader
                .findLastContextMessagesWithinLimits(END_USER_1, 500, 1);

        assertEquals(1, openAiChatContextMessages.size());
        assertEquals(contextMessage2, openAiChatContextMessages.get(0));
    }

    @Test
    public void testFindLastContextMessagesWithinLimitsReturnsLimitedListBecauseOfTokensLimit() {
        OpenAiChatContextMessage contextMessage1 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.USER, CONTENT, 10);
        openAiChatContextMessageRepository.save(contextMessage1);
        OpenAiChatContextMessage contextMessage2 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.ASSISTANT, CONTENT, 100);
        openAiChatContextMessageRepository.save(contextMessage2);
        OpenAiChatContextMessage contextMessage3 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.USER, CONTENT, 100);
        openAiChatContextMessageRepository.save(contextMessage3);

        List<OpenAiChatContextMessage> openAiChatContextMessages = openAiChatContextMessageReader
                .findLastContextMessagesWithinLimits(END_USER_1, 205, 3);

        assertEquals(2, openAiChatContextMessages.size());
        assertEquals(contextMessage3, openAiChatContextMessages.get(0));
        assertEquals(contextMessage2, openAiChatContextMessages.get(1));
    }
}
