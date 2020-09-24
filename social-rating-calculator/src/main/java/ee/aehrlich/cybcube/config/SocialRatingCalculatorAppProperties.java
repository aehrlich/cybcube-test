package ee.aehrlich.cybcube.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "app")
@Validated
@Getter
@Setter
public class SocialRatingCalculatorAppProperties {
    @NotBlank
    private String kafkaTopicName;
}
