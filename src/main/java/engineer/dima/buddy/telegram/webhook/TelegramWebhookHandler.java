package engineer.dima.buddy.telegram.webhook;

import org.springframework.scheduling.annotation.Async;

/**
 * Interface representing a handler for asynchronously processing Telegram webhook requests.
 * Implementations of this interface can be used to define custom behavior for handling
 * different types of Telegram webhook requests, such as text messages, images, voice messages, etc.
 */
public interface TelegramWebhookHandler {
    /**
     * Asynchronously handles the incoming Telegram webhook request.
     *
     * @param telegramWebhookRequest The incoming Telegram webhook request.
     */
    @Async
    void handle(TelegramWebhookRequest telegramWebhookRequest);
}
