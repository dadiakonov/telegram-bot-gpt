package engineer.dima.buddy.openai.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAiChatPropertyTest {
    private final OpenAiChatProperty openAiChatProperty;

    @Autowired
    public OpenAiChatPropertyTest(OpenAiChatProperty openAiChatProperty) {
        this.openAiChatProperty = openAiChatProperty;
    }

    @Test
    void testPropertiesBinding() {
        assertEquals("gpt-4", openAiChatProperty.getModel());
        assertEquals("You are a helpful assistant.", openAiChatProperty.getSystemMessage());
        assertEquals(4, openAiChatProperty.getContextMessagesQty());
        assertEquals(4096, openAiChatProperty.getContextLimitInTokens());
        assertEquals(4096, openAiChatProperty.getResponseLimitInTokens());
    }
}
