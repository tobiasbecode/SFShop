package de.tobiasbecode.sfshop.rating.data.repository;

import de.tobiasbecode.sfshop.rating.data.domain.Rating;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "ratings", path = "ratings")
public interface RatingRepository extends MongoRepository<Rating, String> {

    Rating findBy_id(ObjectId _id);
    Rating idproduct (String idproduct);

}
