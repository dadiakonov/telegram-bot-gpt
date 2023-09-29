package engineer.dima.buddy.telegram.bot;

import engineer.dima.buddy.telegram.TelegramRestTemplateConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Service class responsible for interacting with the Telegram Bot API.
 * It allows sending messages via the Telegram Bot by utilizing the {@link RestTemplate} (see {@link TelegramRestTemplateConfiguration}).
 */
@Service
public class TelegramBot {
    private static String TELEGRAM_BOT_SEND_MESSAGE_ENDPOINT;

    private final RestTemplate restTemplate;
    private final Logger logger;

    /**
     * Constructs a {@link TelegramBot} instance with the provided {@link TelegramBotProperty} and {@link RestTemplate}.
     * Initializes the endpoint URL for sending messages through the Telegram Bot API.
     *
     * @param telegramBotProperty Provides the necessary properties for configuring the Telegram Bot.
     * @param restTemplate        The {@link RestTemplate} to be used for making HTTP requests to the Telegram Bot API.
     */
    public TelegramBot(TelegramBotProperty telegramBotProperty, @Qualifier("telegramRestTemplate") RestTemplate restTemplate) {
        TELEGRAM_BOT_SEND_MESSAGE_ENDPOINT = String.format("https://api.telegram.org/bot%s/sendMessage", telegramBotProperty.getToken());

        this.restTemplate = restTemplate;
        this.logger = LoggerFactory.getLogger(TelegramBot.class);
    }

    /**
     * Sends a message using the Telegram Bot API.
     * Logs the request details and any exceptions encountered during the API call.
     *
     * @param telegramBotMessage The message to be sent via the Telegram Bot.
     */
    public void sendMessage(TelegramBotMessage telegramBotMessage) {
        try {
            logger.info("Calling Telegram API with request {}", telegramBotMessage);

            restTemplate.postForLocation(TELEGRAM_BOT_SEND_MESSAGE_ENDPOINT, telegramBotMessage);
        } catch (RestClientException e) {
            logger.warn("Telegram API client exception", e);
        } catch (Throwable throwable) {
            logger.warn("Telegram API exception", throwable);
        }
    }
}
