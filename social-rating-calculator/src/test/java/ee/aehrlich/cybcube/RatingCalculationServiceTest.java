package ee.aehrlich.cybcube;

import ee.aehrlich.cybcube.common.KafkaMessage;
import ee.aehrlich.cybcube.service.RatingCalculationService;
import ee.aehrlich.cybcube.service.UserSocialRatingScoreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class RatingCalculationServiceTest {

    private final UserSocialRatingScoreService userSocialRatingScoreService = mock(UserSocialRatingScoreService.class);
    private final RatingCalculationService ratingCalculationService = new RatingCalculationService(userSocialRatingScoreService);

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testCalculation() {
        KafkaMessage kafkaMessage = new KafkaMessage("firstName", "lastName", 15, 0.5);
        ratingCalculationService.listenForMessage(kafkaMessage);
        // for test to run in environments with both comma and period as decimal separator
        assertThat(outputStreamCaptor.toString().trim().replace(",", "."))
                .isEqualTo("firstName lastName has 7.500000 score");
    }
    @Test
    void testCalculation_NPE() {
        assertThrows(NullPointerException.class, () -> {
            ratingCalculationService.listenForMessage(null);
        });
    }
}
