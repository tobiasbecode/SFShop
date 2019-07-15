package de.tobiasbecode.sfshop.rating.web.controller;

import de.tobiasbecode.sfshop.rating.data.domain.Rating;
import de.tobiasbecode.sfshop.rating.data.repository.RatingRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Rating Controller -
 * "getRatingByproductId" Method is set to wire products in sfshop-products
 * with specific rating (via productId)
 */


@RestController
@RequestMapping("/ratings")
public class RatingController {


    @Autowired
    private final RatingRepository ratingRepository;

    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @GetMapping(path = "/")
    public List getAllRatings() {
        return ratingRepository.findAll();
    }

    @GetMapping(path = "/{idproduct}")
    public Rating getRatingByproductId(@PathVariable("idproduct") String idproduct) {
        return ratingRepository.idproduct(idproduct);
    }

    @PostMapping(path = "/new")
    public Rating createRating(@Valid @RequestBody Rating rating) {
        rating.set_id(ObjectId.get());
        ratingRepository.save(rating);
        return rating;
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRating(@PathVariable ObjectId id) {
        ratingRepository.delete(ratingRepository.findBy_id(id));
    }

}




