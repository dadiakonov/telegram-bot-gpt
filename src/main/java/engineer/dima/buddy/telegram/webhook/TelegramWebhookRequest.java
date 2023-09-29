package engineer.dima.buddy.telegram.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import engineer.dima.buddy.telegram.TelegramMessage;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TelegramWebhookRequest(@NotNull TelegramMessage message) {
}
