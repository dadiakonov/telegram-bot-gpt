package engineer.dima.buddy.openai.chat;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.EncodingRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiChatEncodingRegistryConfiguration {
    @Bean
    @Qualifier("openAiChatEncodingRegistryConfiguration")
    public EncodingRegistry encodingRegistry() {
        return Encodings.newDefaultEncodingRegistry();
    }
}
