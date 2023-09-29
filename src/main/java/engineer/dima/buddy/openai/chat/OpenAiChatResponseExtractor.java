package engineer.dima.buddy.openai.chat;

import engineer.dima.buddy.openai.OpenAiUnexpectedResponseException;

import java.util.List;

public class OpenAiChatResponseExtractor {
    public static String extractContent(OpenAiChatResponse openAiChatResponse) throws OpenAiUnexpectedResponseException {
        if (openAiChatResponse == null) {
            throw new OpenAiUnexpectedResponseException("Response body is null");
        }

        List<OpenAiChatChoice> openAiChatChoices = openAiChatResponse.choices();
        if (openAiChatChoices == null || openAiChatChoices.isEmpty()) {
            throw new OpenAiUnexpectedResponseException("Response body does not have choices");
        }

        OpenAiChatMessage openAiChatMessage = openAiChatChoices.get(0).message();
        if (openAiChatMessage == null) {
            throw new OpenAiUnexpectedResponseException("Response body does not have a message");
        }

        String content = openAiChatMessage.content();
        if (content == null || content.isEmpty()) {
            throw new OpenAiUnexpectedResponseException("Response body does not have a message content");
        }

        return content;
    }

    public static Integer extractCompletionTokens(OpenAiChatResponse openAiChatResponse) throws OpenAiUnexpectedResponseException {
        if (openAiChatResponse == null) {
            throw new OpenAiUnexpectedResponseException("Response body is null");
        }

        OpenAiChatUsage openAiChatUsage = openAiChatResponse.usage();
        if (openAiChatUsage == null) {
            throw new OpenAiUnexpectedResponseException("Response body does not have usage");
        }

        Integer completionTokens = openAiChatUsage.completionTokens();
        if (completionTokens == null) {
            throw new OpenAiUnexpectedResponseException("Response body does not have completion tokens");
        }

        return completionTokens;
    }
}
