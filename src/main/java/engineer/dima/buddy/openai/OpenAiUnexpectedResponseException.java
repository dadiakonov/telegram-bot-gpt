package engineer.dima.buddy.openai;

public class OpenAiUnexpectedResponseException extends RuntimeException {
    public OpenAiUnexpectedResponseException(String message) {
        super(message);
    }
}
