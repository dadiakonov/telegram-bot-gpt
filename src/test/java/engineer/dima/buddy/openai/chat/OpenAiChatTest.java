package engineer.dima.buddy.openai.chat;

import engineer.dima.buddy.openai.OpenAiApiClientException;
import engineer.dima.buddy.openai.OpenAiUnexpectedResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class OpenAiChatTest {
    private final OpenAiChat openAiChat;
    private final RestTemplate restTemplate;
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    OpenAiChatTest(OpenAiChat openAiChat, @Qualifier("openaiRestTemplate") RestTemplate restTemplate) {
        this.openAiChat = openAiChat;
        this.restTemplate = restTemplate;
    }

    @BeforeEach
    public void setUp() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testSendMessageSuccessful() {
        String mockResponseJson = """
                {
                  "id": "chatcmpl-123",
                  "object": "chat.completion",
                  "created": 1677652288,
                  "model": "gpt-3.5-turbo-0613",
                  "choices": [{
                    "index": 0,
                    "message": {
                      "role": "assistant",
                      "content": "Thanks, I'm fine. How may I assist you today?"
                    },
                    "finish_reason": "stop"
                  }],
                  "usage": {
                    "prompt_tokens": 7,
                    "completion_tokens": 13,
                    "total_tokens": 20
                  }
                }""";


        mockRestServiceServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andRespond(withSuccess(mockResponseJson, MediaType.APPLICATION_JSON));

        String responseContent = openAiChat.sendMessage(1L, "Hi, how are you doing?");

        assertEquals("Thanks, I'm fine. How may I assist you today?", responseContent);

        mockRestServiceServer.verify();
    }

    @Test
    public void testSendMessageThrowsOpenAiApiClientException() {
        mockRestServiceServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andRespond(withBadRequest());

        assertThrows(OpenAiApiClientException.class, () -> openAiChat.sendMessage(1L, "Hi, how are you doing?"));

        mockRestServiceServer.verify();
    }

    @Test
    public void testSendMessageThrowsOpenAiUnexpectedResponseException() {
        mockRestServiceServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andRespond(withSuccess("{\"field\":\"value\"}", MediaType.APPLICATION_JSON));

        assertThrows(OpenAiUnexpectedResponseException.class, () -> openAiChat.sendMessage(1L, "Hi, how are you doing?"));

        mockRestServiceServer.verify();
    }
}
