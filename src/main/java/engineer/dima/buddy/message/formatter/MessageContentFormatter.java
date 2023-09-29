package engineer.dima.buddy.message.formatter;

/**
 * Represents a strategy interface for formatting message content. Implementations of
 * this interface can be used to apply various formatting operations on a message string.
 */
public interface MessageContentFormatter {
    /**
     * Formats the given message content string.
     *
     * @param content The message content to be formatted.
     * @return The formatted message content.
     */
    String format(String content);
}
