package engineer.dima.buddy.message.formatter;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents an implementation of {@link MessageContentFormatter} that escapes
 * the main HTML symbols in the given message content. The formatter replaces
 * the symbols &lt;, &gt;, and &amp; with their corresponding HTML entities
 * &amp;lt;, &amp;gt;, and &amp;amp; respectively.
 */
public class MessageContentEscapeMainHtmlSymbolsFormatter implements MessageContentFormatter {
    /**
     * Formats the given message content by escaping the main HTML symbols.
     *
     * @param content The message content to be formatted.
     * @return The message content with the main HTML symbols escaped.
     */
    @Override
    public String format(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }

        Map<Character, String> replacements = Map.of(
                '<', "&lt;",
                '>', "&gt;",
                '&', "&amp;"
        );

        return content.chars()
                .mapToObj(c -> (char) c)
                .map(c -> replacements.getOrDefault(c, String.valueOf(c)))
                .collect(Collectors.joining());
    }
}
