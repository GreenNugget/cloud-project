package nubes.booktify.service;

import java.util.Optional;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nubes.booktify.config.JwtTokenUtil;
import nubes.booktify.exception.NotFoundException;
import nubes.booktify.model.Rating;

import nubes.booktify.model.request.RatingRequest;
import nubes.booktify.model.request.UpdateRatingRequest;
import nubes.booktify.repository.RatingRepository;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public Rating getRating(Integer ratingId) {
        Optional<Rating> rating = this.ratingRepository.findById(ratingId);

        if(!rating.isPresent()) {
            throw new NotFoundException("No se encontró el Rating solicitado");
        }

        return rating.get();
    }

    public List<Rating> getRatingsByUserId(Integer userId) {
        Optional<List<Rating>> ratings = this.ratingRepository.findByUserUserId(userId);

        if(!ratings.isPresent()) {
            throw new NotFoundException("No se encontraron calificaciones");
        }

        return ratings.get();
    }

    public Rating getRatingByUserIdBookId(Integer userId, Integer bookId) {
        List<Rating> ratings = this.getRatingsByUserId(userId);
        return ratings.stream().filter(rating -> bookId.equals(rating.getBook().getBookId())).findFirst().orElse(null);
    }

    @Transactional
    public Rating postRating(RatingRequest ratingRequest) {

        Rating rating = new Rating();

        rating.setUser(userService.getUserById(ratingRequest.getUserId()));
        rating.setBook(bookService.searchBookById(ratingRequest.getBookId()).get(0));

        rating.setScore(ratingRequest.getScore());
        rating.setComment(ratingRequest.getComment());


        rating = this.ratingRepository.save(rating);

        return rating;
    }

    @Transactional
    public Rating updateRating(Integer userId, Integer bookId, UpdateRatingRequest uRatingRequest){
        Rating rating = this.getRatingByUserIdBookId(userId, bookId);

        rating.setScore(uRatingRequest.getScore());
        rating.setComment(uRatingRequest.getComment());
        
        return ratingRepository.save(rating);
    }

    @Transactional
    public Rating updateRating(Integer ratingId, UpdateRatingRequest updateRatingRequest) {
        Rating rating = this.getRating(ratingId);

        rating.setScore(updateRatingRequest.getScore());
        rating.setComment(updateRatingRequest.getComment());

        rating = this.ratingRepository.save(rating);

        return rating;
    }

    @Transactional
    public void deleteRating(Integer userId, Integer bookId) {
        Rating rating = this.getRatingByUserIdBookId(userId, bookId);
        this.ratingRepository.delete(rating);
    }

    @Transactional
    public void deleteRating(Integer ratingId) {
        Rating rating = this.getRating(ratingId);

        this.ratingRepository.delete(rating);
    }
}