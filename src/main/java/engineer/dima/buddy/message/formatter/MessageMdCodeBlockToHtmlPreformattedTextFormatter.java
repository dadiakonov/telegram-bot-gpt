package engineer.dima.buddy.message.formatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an implementation of {@link MessageContentFormatter} that converts
 * Markdown code blocks to HTML preformatted text blocks in the given message content.
 * This formatter recognizes code blocks surrounded by triple backticks (```) and
 * replaces them with &lt;pre&gt; HTML tags, preserving the content inside the block.
 */
public class MessageMdCodeBlockToHtmlPreformattedTextFormatter implements MessageContentFormatter {
    /**
     * Formats the given message content by converting Markdown code blocks
     * to HTML preformatted text blocks.
     *
     * @param content The message content to be formatted.
     * @return The message content with Markdown code blocks converted to HTML preformatted text.
     */
    @Override
    public String format(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }

        Pattern pattern = Pattern.compile("^\\s*```[a-zA-Z0-9]*\\n(.*?)\\n\\s*```", Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String codeBlock = matcher.group(1).trim();
            String replacement = String.format("<pre>%n%s%n</pre>", codeBlock);
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
