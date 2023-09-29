package engineer.dima.buddy.telegram;

import engineer.dima.buddy.telegram.bot.TelegramBotProperty;
import engineer.dima.buddy.telegram.webhook.TelegramWebhookHandler;
import engineer.dima.buddy.telegram.webhook.TelegramWebhookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling incoming webhook requests from Telegram.
 * Defines endpoints related to Telegram bot operations and manages webhook interactions.
 */
@RestController
@RequestMapping("/telegram")
public class TelegramController {
    private final TelegramBotProperty telegramBotProperty;
    private final TelegramWebhookHandler telegramWebhookHandler;
    private final Logger logger;

    /**
     * Constructs a {@link TelegramController} instance with the provided {@link TelegramBotProperty} and {@link TelegramWebhookHandler}.
     *
     * @param telegramBotProperty   Provides the necessary properties for configuring the Telegram Bot.
     * @param telegramWebhookHandler Responsible for asynchronously handling incoming Telegram webhook requests.
     */
    @Autowired
    public TelegramController(TelegramBotProperty telegramBotProperty, TelegramWebhookHandler telegramWebhookHandler) {
        this.telegramBotProperty = telegramBotProperty;
        this.telegramWebhookHandler = telegramWebhookHandler;
        this.logger = LoggerFactory.getLogger(TelegramController.class);
    }

    /**
     * Handles incoming webhook requests from Telegram.
     * Verifies the secret token and the user's ID before processing the request.
     * If verification fails, a {@link TelegramForbiddenAccessException} is thrown.
     * Otherwise, delegates the asynchronous handling of the request to the {@link TelegramWebhookHandler}.
     *
     * @param telegramWebhookRequest The incoming webhook request from Telegram.
     * @param secretToken            The secret token for verifying the request source.
     * @return ResponseEntity<Void>  Returns an OK (200) status code upon successful handling of the request.
     * @throws TelegramForbiddenAccessException If the secret token or the user's ID verification fails.
     */
    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody TelegramWebhookRequest telegramWebhookRequest,
                                  @RequestHeader(name = "X-Telegram-Bot-Api-Secret-Token") String secretToken) {
        if (!telegramBotProperty.getSecretToken().equals(secretToken)) {
            logger.debug("Incorrect telegram secret token: {}", secretToken);
            throw new TelegramForbiddenAccessException();
        }

        logger.info("Telegram webhook request: {}", telegramWebhookRequest);

        Long telegramUserId = telegramWebhookRequest.message().from().id();
        if (!telegramBotProperty.getWhitelistedUsersId().contains(telegramUserId)) {
            logger.info("Forbidden access for telegram user: {}", telegramUserId);
            throw new TelegramForbiddenAccessException();
        }

        telegramWebhookHandler.handle(telegramWebhookRequest);

        logger.info("Telegram webhook response: ok");
        return ResponseEntity.ok().build();
    }
}
