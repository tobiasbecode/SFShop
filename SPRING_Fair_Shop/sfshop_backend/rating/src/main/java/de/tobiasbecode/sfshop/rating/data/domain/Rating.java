package de.tobiasbecode.sfshop.rating.data.domain;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "ratings")
@Getter
@Setter
public class Rating {

    @Id
    private ObjectId _id;

    private int ratingLevel;
    private String ratingText;

    private String idproduct;
    private String ratingName;


    //ObjectId Conversion
    public String get_id() {
        return _id.toHexString();
    }

}
