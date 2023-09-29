package engineer.dima.buddy.openai.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class OpenAiChatMessageProcessingStrategyTest {
    @MockBean
    private OpenAiChat openAiChat;

    private final OpenAiChatMessageProcessingStrategy openAiChatMessageProcessingStrategy;

    @Autowired
    OpenAiChatMessageProcessingStrategyTest(OpenAiChatMessageProcessingStrategy openAiChatMessageProcessingStrategy) {
        this.openAiChatMessageProcessingStrategy = openAiChatMessageProcessingStrategy;
    }

    @Test
    void testProcessMessage() {
        String openAiChatReturnedMessage = """
                <div>Tom & Jerry</div>
                ```java
                <div>Tom & Jerry</div>
                ```
                    ```java
                    <div>Tom & Jerry</div>
                    ```
                <div>Tom & Jerry</div>
                """;
        when(openAiChat.sendMessage(1L, "some text")).thenReturn(openAiChatReturnedMessage);

        String returnedMessage = openAiChatMessageProcessingStrategy.processMessage(1L, "some text");
        System.out.println(returnedMessage);
        String expectedReturnedMessage = """
                &lt;div&gt;Tom &amp; Jerry&lt;/div&gt;
                <pre>
                &lt;div&gt;Tom &amp; Jerry&lt;/div&gt;
                </pre>
                <pre>
                &lt;div&gt;Tom &amp; Jerry&lt;/div&gt;
                </pre>
                &lt;div&gt;Tom &amp; Jerry&lt;/div&gt;
                """;

        assertEquals(expectedReturnedMessage, returnedMessage);
    }
}