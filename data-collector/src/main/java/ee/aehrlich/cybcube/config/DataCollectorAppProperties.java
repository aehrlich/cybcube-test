package ee.aehrlich.cybcube.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class DataCollectorAppProperties {
    @PositiveOrZero @Max(1)
    private Double seed;
    @NotBlank
    private String kafkaTopicName;
}
