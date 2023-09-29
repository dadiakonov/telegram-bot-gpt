package engineer.dima.buddy.openai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAiPropertyTest {
    private final OpenAiProperty openAiProperty;

    @Autowired
    public OpenAiPropertyTest(OpenAiProperty openAiProperty) {
        this.openAiProperty = openAiProperty;
    }

    @Test
    void testPropertiesBinding() {
        assertEquals("sk-jwLcciEFwCJIWoNlDLBZpwxmQQqBuxxNYFoxmQQqBuxxNYFo", openAiProperty.getApiKey());
    }
}
