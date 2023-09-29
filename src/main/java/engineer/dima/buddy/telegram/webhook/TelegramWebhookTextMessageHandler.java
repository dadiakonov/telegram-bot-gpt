package engineer.dima.buddy.telegram.webhook;

import engineer.dima.buddy.message.MessageProcessingStrategy;
import engineer.dima.buddy.telegram.TelegramMessage;
import engineer.dima.buddy.telegram.TelegramParseMode;
import engineer.dima.buddy.telegram.bot.TelegramBot;
import engineer.dima.buddy.telegram.bot.TelegramBotMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service class for handling text messages received via Telegram webhooks.
 * Implements {@link TelegramWebhookHandler}.
 * This class is capable of processing general text messages and specific commands like "/start".
 */
@Service
public class TelegramWebhookTextMessageHandler implements TelegramWebhookHandler {
    private final MessageProcessingStrategy messageProcessingStrategy;
    private final TelegramBot telegramBot;
    private final Logger logger;

    /**
     * Constructs a new instance of {@link TelegramWebhookTextMessageHandler}.
     *
     * @param messageProcessingStrategy The strategy for processing incoming text messages.
     * @param telegramBot The Telegram bot service used for sending messages.
     */
    @Autowired
    public TelegramWebhookTextMessageHandler(MessageProcessingStrategy messageProcessingStrategy, TelegramBot telegramBot) {
        this.messageProcessingStrategy = messageProcessingStrategy;
        this.telegramBot = telegramBot;
        this.logger = LoggerFactory.getLogger(TelegramWebhookTextMessageHandler.class);
    }

    /**
     * Asynchronously handles incoming Telegram webhook requests.
     * Distinguishes between the "/start" command and other text messages, processing them accordingly.
     *
     * @param telegramWebhookRequest The incoming webhook request from Telegram.
     */
    @Async
    @Override
    public void handle(TelegramWebhookRequest telegramWebhookRequest) {
        if (telegramWebhookRequest.message().text().equals("/start")) {
            handleStart(telegramWebhookRequest);
            return;
        }

        handleTextMessage(telegramWebhookRequest);
    }

    /**
     * Handles the "/start" command from Telegram by sending a welcome message.
     *
     * @param telegramWebhookRequest The incoming webhook request containing the "/start" command.
     */
    private void handleStart(TelegramWebhookRequest telegramWebhookRequest) {
        logger.debug("Handle telegram /start command");

        TelegramMessage telegramMessage = telegramWebhookRequest.message();

        TelegramBotMessage telegramBotMessage = new TelegramBotMessage(telegramMessage.chat().id(), "Hello! How can I assist you?",
                telegramMessage.messageId(), TelegramParseMode.HTML);

        telegramBot.sendMessage(telegramBotMessage);
    }

    /**
     * Handles general text messages from Telegram by processing them using the {@link MessageProcessingStrategy}
     * and sending a response.
     *
     * @param telegramWebhookRequest The incoming webhook request containing the text message.
     */
    private void handleTextMessage(TelegramWebhookRequest telegramWebhookRequest) {
        logger.debug("Handle telegram text message");

        TelegramMessage telegramMessage = telegramWebhookRequest.message();

        String responseContent = messageProcessingStrategy.processMessage(telegramMessage.from().id(), telegramMessage.text());

        TelegramBotMessage telegramBotMessage = new TelegramBotMessage(telegramMessage.chat().id(), responseContent,
                telegramMessage.messageId(), TelegramParseMode.HTML);

        telegramBot.sendMessage(telegramBotMessage);
    }
}
