package engineer.dima.buddy.telegram;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration representing the available parsing modes for text messages in Telegram.
 * Different parse modes allow for different styles of text formatting.
 */
public enum TelegramParseMode {
    MARKDOWN("Markdown"),
    MARKDOWN_V2("MarkdownV2"),
    HTML("html");

    private final String jsonValue;

    TelegramParseMode(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }
}
