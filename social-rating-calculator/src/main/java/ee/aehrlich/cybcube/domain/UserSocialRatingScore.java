package ee.aehrlich.cybcube.domain;

import ee.aehrlich.cybcube.common.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserSocialRatingScore implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String userId;
    private final Double score;

    public static String userIdFromKafkaMessage(KafkaMessage message) {
        return message.getFirstName() + " " + message.getLastName();
    }
}
