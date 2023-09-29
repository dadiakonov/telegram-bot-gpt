package engineer.dima.buddy.openai.chat.context;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenAiChatContextMessageRepository extends CrudRepository<OpenAiChatContextMessage, Long> {
    List<OpenAiChatContextMessage> findAllByEndUser(String endUser, Pageable pageable);
}
