package engineer.dima.buddy.openai.chat;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OpenAiChatRole {
    SYSTEM, USER, ASSISTANT, FUNCTION;

    @JsonValue
    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
