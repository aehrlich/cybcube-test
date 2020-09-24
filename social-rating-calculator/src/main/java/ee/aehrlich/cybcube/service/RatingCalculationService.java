package ee.aehrlich.cybcube.service;

import ee.aehrlich.cybcube.common.KafkaMessage;
import ee.aehrlich.cybcube.domain.UserSocialRatingScore;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RatingCalculationService {

    private final UserSocialRatingScoreService userSocialRatingScoreService;

    public RatingCalculationService(UserSocialRatingScoreService userSocialRatingScoreService) {
        this.userSocialRatingScoreService = userSocialRatingScoreService;
    }

    @KafkaListener(topics = "${app.kafka-topic-name}")
    public void listenForMessage(KafkaMessage payload) {
        Double rating = payload.getAge() * payload.getSeed();
        // creating a separate no-format logger looked like an overkill here
        System.out.printf("%s %s has %f score%n",
                payload.getFirstName(), payload.getLastName(), rating);
        userSocialRatingScoreService.save(new UserSocialRatingScore(
                UserSocialRatingScore.userIdFromKafkaMessage(payload), rating));
    }

}
