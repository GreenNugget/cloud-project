package nubes.booktify.rest;

import javax.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nubes.booktify.model.Rating;
import nubes.booktify.model.request.RatingRequest;
import nubes.booktify.model.request.UpdateRatingRequest;
import nubes.booktify.service.RatingService;

@RestController
@RequestMapping("/api/v1")
public class RatingRest {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> getRating(@PathVariable Integer ratingId) {
        Rating rating = this.ratingService.getRating(ratingId);

        return ResponseEntity.ok().body(rating);
    }

    @GetMapping("/ratings/users/{userId}")
    public ResponseEntity<List<Rating>> getUserRatings(@PathVariable Integer userId) {
        List<Rating> ratings = this.ratingService.getRatingsByUserId(userId);
        
        return ResponseEntity.ok().body(ratings);
    }

    @GetMapping("/ratings/users/{userId}/books/{bookId}")
    public ResponseEntity<Rating> getRating(@PathVariable Integer userId, @PathVariable Integer bookId) {
        Rating ratings = this.ratingService.getRatingByUserIdBookId(userId, bookId);
        
        return ResponseEntity.ok().body(ratings);
    }

    @PostMapping("/ratings")
    public ResponseEntity<Rating> postRating(@RequestBody @Valid RatingRequest ratingRequest) {
        Rating rating = this.ratingService.postRating(ratingRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }

    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable Integer ratingId, @RequestBody @Valid UpdateRatingRequest ratingRequest) {
        Rating rating = ratingService.updateRating(ratingId, ratingRequest);

        return ResponseEntity.ok().body(rating);
    }

    @PutMapping("/ratings/users/{userId}/books/{bookId}")
    public ResponseEntity<Rating> updateRating(@PathVariable Integer userId, @PathVariable Integer bookId, @RequestBody @Valid UpdateRatingRequest ratingRequest) {
        return ResponseEntity.ok().body(ratingService.updateRating(userId, bookId, ratingRequest));
    }

    @DeleteMapping("/ratings/users/{userId}/books/{bookId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Integer userId, @PathVariable Integer bookId) {
        this.ratingService.deleteRating(userId, bookId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/ratings/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Integer ratingId) {
        this.ratingService.deleteRating(ratingId);

        return ResponseEntity.ok().build();
    }

}