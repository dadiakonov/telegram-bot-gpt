package engineer.dima.buddy.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = {TelegramController.class})
public class TelegramExceptionHandler {
    private final Logger logger;

    public TelegramExceptionHandler() {
        this.logger = LoggerFactory.getLogger(TelegramExceptionHandler.class);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            TelegramForbiddenAccessException.class,
    })
    public ResponseEntity<Void> handleForbiddenException(Throwable e) {
        logger.debug("Handle forbidden exception", e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
