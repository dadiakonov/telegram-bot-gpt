package engineer.dima.buddy.telegram.bot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "telegram.bot.whitelisted-users-id=1,2,3"
})
class TelegramBotPropertyTest {
    private final TelegramBotProperty telegramBotProperty;

    @Autowired
    TelegramBotPropertyTest(TelegramBotProperty telegramBotProperty) {
        this.telegramBotProperty = telegramBotProperty;
    }

    @Test
    void testPropertiesBinding() {
        assertEquals("12345678910:jwLcciEFwCJIWoNlDLBZpwxmQQqBuxxNYFo", telegramBotProperty.getToken());
        assertEquals("secret", telegramBotProperty.getSecretToken());
        assertEquals(List.of(1L, 2L, 3L), telegramBotProperty.getWhitelistedUsersId());
    }
}
