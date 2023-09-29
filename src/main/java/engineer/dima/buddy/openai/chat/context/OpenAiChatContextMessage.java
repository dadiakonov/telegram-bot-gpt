package engineer.dima.buddy.openai.chat.context;

import engineer.dima.buddy.openai.chat.OpenAiChatRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "open_ai_chat_context_message")
public class OpenAiChatContextMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "open_ai_chat_context_message_id")
    private Long openAiChatContextMessageId;

    @Column(name = "end_user", nullable = false)
    private String endUser;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private OpenAiChatRole role;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "tokens", nullable = false)
    private Integer tokens;

    public OpenAiChatContextMessage(String endUser, OpenAiChatRole role, String content, Integer tokens) {
        this.endUser = endUser;
        this.role = role;
        this.content = content;
        this.tokens = tokens;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OpenAiChatContextMessage that = (OpenAiChatContextMessage) o;
        return getOpenAiChatContextMessageId() != null && Objects.equals(getOpenAiChatContextMessageId(), that.getOpenAiChatContextMessageId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
