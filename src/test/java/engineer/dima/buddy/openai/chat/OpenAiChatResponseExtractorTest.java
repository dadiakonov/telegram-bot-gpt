package engineer.dima.buddy.openai.chat;

import engineer.dima.buddy.openai.OpenAiUnexpectedResponseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpenAiChatResponseExtractorTest {
    @Mock
    private List<OpenAiChatChoice> openAiChatChoicesMock;
    @Mock
    private OpenAiChatUsage openAiChatUsageMock;

    @Test
    void extractContentShouldReturnContentWhenResponseIsValid() throws OpenAiUnexpectedResponseException {
        String expectedContent = "some content";
        OpenAiChatMessage openAiChatMessage = new OpenAiChatMessage(OpenAiChatRole.ASSISTANT, expectedContent);
        OpenAiChatChoice openAiChatChoice = new OpenAiChatChoice(1, openAiChatMessage, "");
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(List.of(openAiChatChoice), openAiChatUsageMock);

        String actualContent = OpenAiChatResponseExtractor.extractContent(openAiChatResponse);

        assertEquals(expectedContent, actualContent);
    }

    @Test
    void extractContentShouldThrowExceptionWhenResponseBodyIsNull() {
        Exception exception = assertThrows(OpenAiUnexpectedResponseException.class,
                () -> OpenAiChatResponseExtractor.extractContent(null));

        assertEquals("Response body is null", exception.getMessage());
    }

    @Test
    void extractContentShouldThrowExceptionWhenChoicesAreNull() {
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(null, openAiChatUsageMock);

        Exception exception = assertThrows(OpenAiUnexpectedResponseException.class,
                () -> OpenAiChatResponseExtractor.extractContent(openAiChatResponse));

        assertEquals("Response body does not have choices", exception.getMessage());
    }

    @Test
    void extractContentShouldThrowExceptionWhenChoicesAreEmpty() {
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(List.of(), openAiChatUsageMock);

        Exception exception = assertThrows(OpenAiUnexpectedResponseException.class,
                () -> OpenAiChatResponseExtractor.extractContent(openAiChatResponse));

        assertEquals("Response body does not have choices", exception.getMessage());
    }

    @Test
    void extractContentShouldThrowExceptionWhenMessageIsNull() {
        OpenAiChatChoice openAiChatChoice = new OpenAiChatChoice(1, null, "");
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(List.of(openAiChatChoice), openAiChatUsageMock);

        Exception exception = assertThrows(OpenAiUnexpectedResponseException.class,
                () -> OpenAiChatResponseExtractor.extractContent(openAiChatResponse));

        assertEquals("Response body does not have a message", exception.getMessage());
    }

    @Test
    void extractContentShouldThrowExceptionWhenContentIsNull() {
        OpenAiChatMessage openAiChatMessage = new OpenAiChatMessage(OpenAiChatRole.ASSISTANT, null);
        OpenAiChatChoice openAiChatChoice = new OpenAiChatChoice(1, openAiChatMessage, "");
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(List.of(openAiChatChoice), openAiChatUsageMock);

        Exception exception = assertThrows(OpenAiUnexpectedResponseException.class,
                () -> OpenAiChatResponseExtractor.extractContent(openAiChatResponse));

        assertEquals("Response body does not have a message content", exception.getMessage());
    }

    @Test
    void extractContentShouldThrowExceptionWhenContentIsEmpty() {
        OpenAiChatMessage openAiChatMessage = new OpenAiChatMessage(OpenAiChatRole.ASSISTANT, "");
        OpenAiChatChoice openAiChatChoice = new OpenAiChatChoice(1, openAiChatMessage, "");
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(List.of(openAiChatChoice), openAiChatUsageMock);

        Exception exception = assertThrows(OpenAiUnexpectedResponseException.class,
                () -> OpenAiChatResponseExtractor.extractContent(openAiChatResponse));

        assertEquals("Response body does not have a message content", exception.getMessage());
    }

    @Test
    void extractCompletionTokensShouldReturnCompletionTokensWhenResponseIsValid() {
        Integer expectedCompletionTokens = 50;
        OpenAiChatUsage openAiChatUsage = new OpenAiChatUsage(expectedCompletionTokens);
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(openAiChatChoicesMock, openAiChatUsage);

        Integer actualCompletionTokens = OpenAiChatResponseExtractor.extractCompletionTokens(openAiChatResponse);

        assertEquals(expectedCompletionTokens, actualCompletionTokens);
    }

    @Test
    void extractCompletionTokensShouldThrowExceptionWhenUsageIsNull() {
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(openAiChatChoicesMock, null);

        Exception exception = assertThrows(OpenAiUnexpectedResponseException.class,
                () -> OpenAiChatResponseExtractor.extractCompletionTokens(openAiChatResponse));

        assertEquals("Response body does not have usage", exception.getMessage());
    }
    @Test
    void extractCompletionTokensShouldThrowExceptionWhenCompletionTokensIsNull() {
        OpenAiChatUsage openAiChatUsage = new OpenAiChatUsage(null);
        OpenAiChatResponse openAiChatResponse = new OpenAiChatResponse(openAiChatChoicesMock, openAiChatUsage);

        Exception exception = assertThrows(OpenAiUnexpectedResponseException.class,
                () -> OpenAiChatResponseExtractor.extractCompletionTokens(openAiChatResponse));

        assertEquals("Response body does not have completion tokens", exception.getMessage());
    }
}
