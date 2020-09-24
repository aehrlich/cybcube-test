package ee.aehrlich.cybcube.service;

import ee.aehrlich.cybcube.common.KafkaMessage;
import ee.aehrlich.cybcube.config.DataCollectorAppProperties;
import ee.aehrlich.cybcube.domain.RatingCalculationRequestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class RatingCalculationRequestService {

    @Autowired
    private DataCollectorAppProperties appProperties;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void requestRatingCalculation(RatingCalculationRequestData request) {
        KafkaMessage message = new KafkaMessage(request.getFirstName(), request.getLastName(),
                request.getAge(), appProperties.getSeed());

        // Shall we really do something with the Future here? REST client won't wait
        ListenableFuture<SendResult<String, Object>> future =
                template.send(appProperties.getKafkaTopicName(), message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Message [{}] delivered with offset {}",
                        message,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to deliver message [{}]. {}",
                        message,
                        ex.getMessage());
            }
        });

    }
}
