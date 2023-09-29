package engineer.dima.buddy.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TelegramMessage(@NotNull TelegramChat chat, @NotNull Long messageId, @NotNull TelegramUser from, @NotNull String text) {
}
