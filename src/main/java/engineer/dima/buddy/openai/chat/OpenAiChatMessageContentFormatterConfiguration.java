package engineer.dima.buddy.openai.chat;

import engineer.dima.buddy.message.formatter.MessageContentEscapeMainHtmlSymbolsFormatter;
import engineer.dima.buddy.message.formatter.MessageMdCodeBlockToHtmlPreformattedTextFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Configuration
@Profile("openAiChat")
public class OpenAiChatMessageContentFormatterConfiguration {
    @Bean
    @Order(1)
    public MessageContentEscapeMainHtmlSymbolsFormatter messageContentEscapeMainHtmlSymbolsFormatter() {
        return new MessageContentEscapeMainHtmlSymbolsFormatter();
    }

    @Bean
    @Order(2)
    public MessageMdCodeBlockToHtmlPreformattedTextFormatter messageMdCodeBlockToHtmlPreformattedTextFormatter() {
        return new MessageMdCodeBlockToHtmlPreformattedTextFormatter();
    }
}
