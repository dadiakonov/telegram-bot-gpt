package engineer.dima.buddy.telegram;

import engineer.dima.buddy.message.MessageProcessingStrategy;
import engineer.dima.buddy.telegram.bot.TelegramBotProperty;
import engineer.dima.buddy.telegram.webhook.TelegramWebhookHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TelegramController.class)
@AutoConfigureMockMvc
class TelegramControllerTest {
    @MockBean
    private TelegramBotProperty telegramBotProperty;

    @MockBean
    private TelegramWebhookHandler telegramWebhookHandler;

    @MockBean
    private MessageProcessingStrategy messageProcessingStrategy;

    private final MockMvc mockMvc;

    @Autowired
    TelegramControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testWebhookWithCorrectStartRequest() throws Exception {
        when(telegramBotProperty.getWhitelistedUsersId()).thenReturn(List.of(1111111L));
        when(telegramBotProperty.getSecretToken()).thenReturn("secret");

        String jsonRequest = """
                {
                    "update_id":10000,
                    "message":{
                      "date":1441645532,
                      "chat":{
                         "last_name":"Test Lastname",
                         "id":1111111,
                         "type": "private",
                         "first_name":"Test Firstname",
                         "username":"Testusername"
                      },
                      "message_id":1365,
                      "from":{
                         "last_name":"Test Lastname",
                         "id":1111111,
                         "first_name":"Test Firstname",
                         "username":"Testusername"
                      },
                      "text":"/start"
                    }
                }""";

        mockMvc.perform(post("/telegram/webhook")
                .header("X-Telegram-Bot-Api-Secret-Token", "secret")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testWebhookWithCorrectMessageRequest() throws Exception {
        when(telegramBotProperty.getWhitelistedUsersId()).thenReturn(List.of(1L));
        when(telegramBotProperty.getSecretToken()).thenReturn("secret");
        when(messageProcessingStrategy.processMessage(1L, "Hello")).thenReturn("Hi");

        String jsonRequest = """
                {
                    "message":{
                      "from":{
                         "id":1
                      },
                      "text":"Hello"
                    }
                }""";

        mockMvc.perform(post("/telegram/webhook")
                .header("X-Telegram-Bot-Api-Secret-Token", "secret")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testWebhookWithNonWhitelistedUser() throws Exception {
        when(telegramBotProperty.getWhitelistedUsersId()).thenReturn(List.of(1L));
        when(telegramBotProperty.getSecretToken()).thenReturn("secret");

        String jsonRequest = """
                {
                    "message":{
                      "from":{
                         "id":2
                      },
                      "text":"/start"
                    }
                }""";

        mockMvc.perform(post("/telegram/webhook")
                .header("X-Telegram-Bot-Api-Secret-Token", "secret")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    void testWebhookWithIncorrectSecretToken() throws Exception {
        when(telegramBotProperty.getWhitelistedUsersId()).thenReturn(List.of(1L));
        when(telegramBotProperty.getSecretToken()).thenReturn("secret");

        String jsonRequest = """
                {
                    "message":{
                      "from":{
                         "id":1
                      },
                      "text":"/start"
                    }
                }""";

        mockMvc.perform(post("/telegram/webhook")
                        .header("X-Telegram-Bot-Api-Secret-Token", "incorrect-secret")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden());
    }
}
