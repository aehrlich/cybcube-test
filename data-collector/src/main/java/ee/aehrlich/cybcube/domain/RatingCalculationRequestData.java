package ee.aehrlich.cybcube.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.aehrlich.cybcube.common.KafkaMessage;

// using the same KafkaMessage class for both external and internal communication is a lazy anti-pattern
// so lets at least provide a separate class for input (even if it lazily extends KafkaMessage here)
@JsonIgnoreProperties({ "seed" })
public class RatingCalculationRequestData extends KafkaMessage {

}
