package engineer.dima.buddy.message;

/**
 * Represents a strategy for processing messages. Implementations of this interface can define
 * custom behavior for processing different types of messages based on user ID and message content.
 * This allows for flexibility in handling messages and can be used to implement various features
 * such as natural language processing, command execution, etc.
 */
public interface MessageProcessingStrategy {
    /**
     * Processes a message based on the user ID and message content.
     * The implementation can vary depending on the desired behavior and functionality.
     *
     * @param userId  The ID of the user sending the message.
     * @param message The content of the message to be processed.
     * @return The processed message or response.
     */
    String processMessage(Long userId, String message);
}
