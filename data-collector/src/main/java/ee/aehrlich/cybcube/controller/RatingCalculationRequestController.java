package ee.aehrlich.cybcube.controller;

import ee.aehrlich.cybcube.domain.RatingCalculationRequestData;
import ee.aehrlich.cybcube.service.RatingCalculationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RatingCalculationRequestController {
    @Autowired
    private RatingCalculationRequestService service;

    @PostMapping("/requests")
    public ResponseEntity<String> requestRatingCalculation(@Valid @RequestBody RatingCalculationRequestData request) {
        service.requestRatingCalculation(request);
        return ResponseEntity.ok("Rating calculation request accepted.");
    }
}
