package engineer.dima.buddy.openai.chat;

import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(OpenAiChatEncodingRegistryConfiguration.class)
class OpenAiChatEncodingRegistryConfigurationTest {
    private final EncodingRegistry encodingRegistry;

    @Autowired
    OpenAiChatEncodingRegistryConfigurationTest(@Qualifier("openAiChatEncodingRegistryConfiguration") EncodingRegistry encodingRegistry) {
        this.encodingRegistry = encodingRegistry;
    }

    @Test
    void testEncodingRegistry() {
        String content = """
                The GPT family of models process text using tokens, which are common sequences of characters found in text.
                The models understand the statistical relationships between these tokens, and excel at producing the next token in a sequence of tokens.
                """;

        int actualCount = encodingRegistry.getEncoding(EncodingType.CL100K_BASE).countTokens(content);

        assertEquals(44, actualCount);
    }
}