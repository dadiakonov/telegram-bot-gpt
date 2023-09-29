package engineer.dima.buddy.message.formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessageMdCodeBlockToHtmlPreformattedTextFormatterTest {
    private final MessageMdCodeBlockToHtmlPreformattedTextFormatter formatter;

    @Autowired
    MessageMdCodeBlockToHtmlPreformattedTextFormatterTest(MessageMdCodeBlockToHtmlPreformattedTextFormatter formatter) {
        this.formatter = formatter;
    }

    @Test
    void testFormat() {
        String content = """
                text
                ```java
                System.out.println("```content```");
                ```
                    ```java
                    System.out.println("```content```");
                    ```
                ``text`
                ```
                System.out.println(content);
                ```
                text
                x```
                System.out.println(content);
                ```
                ```text```
                """;

        String expectedContent = """
                text
                <pre>
                System.out.println("```content```");
                </pre>
                <pre>
                System.out.println("```content```");
                </pre>
                ``text`
                <pre>
                System.out.println(content);
                </pre>
                text
                x```
                System.out.println(content);
                ```
                ```text```
                """;

        Assertions.assertEquals(expectedContent, formatter.format(content));
    }
}