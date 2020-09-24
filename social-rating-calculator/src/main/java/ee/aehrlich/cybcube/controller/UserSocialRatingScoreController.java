package ee.aehrlich.cybcube.controller;

import ee.aehrlich.cybcube.domain.UserSocialRatingScore;
import ee.aehrlich.cybcube.service.UserSocialRatingScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/redis/score")
public class UserSocialRatingScoreController {

    @Autowired
    UserSocialRatingScoreService service;

    @GetMapping
    public Map<String, UserSocialRatingScore> findAll() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public UserSocialRatingScore findById(@PathVariable("userId") final String userId) {
        return service.findByUserId(userId);
    }

    // POST mapping not provided in spite of service being able to create Redis data,
    // as task description implies data should be created only by consuming Kafka messages.
}
