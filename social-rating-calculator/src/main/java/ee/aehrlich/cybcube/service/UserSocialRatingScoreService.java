package ee.aehrlich.cybcube.service;

import ee.aehrlich.cybcube.domain.UserSocialRatingScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class UserSocialRatingScoreService {

    private static final String USER_SCORE = "USER_SCORE";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, UserSocialRatingScore> hashOperations;

    @PostConstruct
    private void initializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(final UserSocialRatingScore score) {
        hashOperations.put(USER_SCORE, score.getUserId(), score);
    }

    // find* methods are for testing and demo purpose
    public UserSocialRatingScore findByUserId(final String userId) {
        return hashOperations.get(USER_SCORE, userId);
    }

    public Map<String, UserSocialRatingScore> findAll() {
        return hashOperations.entries(USER_SCORE);
    }
}
