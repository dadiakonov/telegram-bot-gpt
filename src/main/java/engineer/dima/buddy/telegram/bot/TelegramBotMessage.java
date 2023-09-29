package engineer.dima.buddy.telegram.bot;

import engineer.dima.buddy.telegram.TelegramParseMode;

public record TelegramBotMessage(Long chatId, String text, Long replyToMessageId, TelegramParseMode parseMode) {
}
