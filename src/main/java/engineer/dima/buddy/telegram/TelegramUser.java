package engineer.dima.buddy.telegram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TelegramUser(@NotNull @Positive Long id) {
}
