package engineer.dima.buddy.message.formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessageContentEscapeMainHtmlSymbolsFormatterTest {
    private final MessageContentEscapeMainHtmlSymbolsFormatter formatter;

    @Autowired
    MessageContentEscapeMainHtmlSymbolsFormatterTest(MessageContentEscapeMainHtmlSymbolsFormatter formatter) {
        this.formatter = formatter;
    }

    @Test
    void testFormat() {
        String content = """
                <div>
                Tom & Jerry
                </div>
                """;

        String expectedContent = """
                &lt;div&gt;
                Tom &amp; Jerry
                &lt;/div&gt;
                """;

        Assertions.assertEquals(expectedContent, formatter.format(content));
    }
}