package engineer.dima.buddy.openai.chat.context;

import engineer.dima.buddy.openai.chat.OpenAiChatRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OpenAiChatContextMessageRepositoryTest {
    private static final String END_USER_1 = "1";
    private static final String END_USER_2 = "2";
    private static final String END_USER_3 = "3";
    private static final String CONTENT = "some content";
    private static final Integer TOKENS = 10;

    private final TestEntityManager entityManager;
    private final OpenAiChatContextMessageRepository repository;

    @Autowired
    OpenAiChatContextMessageRepositoryTest(TestEntityManager entityManager, OpenAiChatContextMessageRepository repository) {
        this.entityManager = entityManager;
        this.repository = repository;
    }

    @Test
    void testFindAllByUser() {
        OpenAiChatContextMessage contextMessage1 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.USER, CONTENT, TOKENS);
        entityManager.persist(contextMessage1);
        OpenAiChatContextMessage contextMessage2 = new OpenAiChatContextMessage(END_USER_2, OpenAiChatRole.USER, CONTENT, TOKENS);
        entityManager.persist(contextMessage2);
        OpenAiChatContextMessage contextMessage3 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.USER, CONTENT, TOKENS);
        entityManager.persist(contextMessage3);
        OpenAiChatContextMessage contextMessage4 = new OpenAiChatContextMessage(END_USER_2, OpenAiChatRole.ASSISTANT, CONTENT, TOKENS);
        entityManager.persist(contextMessage4);
        OpenAiChatContextMessage contextMessage5 = new OpenAiChatContextMessage(END_USER_1, OpenAiChatRole.ASSISTANT, CONTENT, TOKENS);
        entityManager.persist(contextMessage5);
        OpenAiChatContextMessage contextMessage6 = new OpenAiChatContextMessage(END_USER_2, OpenAiChatRole.ASSISTANT, CONTENT, TOKENS);
        entityManager.persist(contextMessage6);
        entityManager.flush();

        List<OpenAiChatContextMessage> firstEndUserContextMessages = repository.findAllByEndUser(END_USER_1, PageRequest.of(1, 1));
        assertThat(firstEndUserContextMessages).containsOnly(contextMessage3);

        List<OpenAiChatContextMessage> secondEndUserContextMessages = repository.findAllByEndUser(END_USER_2, PageRequest.of(0, 2));
        assertThat(secondEndUserContextMessages).containsOnly(contextMessage2, contextMessage4);

        List<OpenAiChatContextMessage> thirdEndUserContextMessages = repository.findAllByEndUser(END_USER_3, PageRequest.of(0, 3));
        assertThat(thirdEndUserContextMessages).isEmpty();
    }
}