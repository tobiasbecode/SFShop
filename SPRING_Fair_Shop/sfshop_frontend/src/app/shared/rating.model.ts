export class Rating {

    ratingText: string;
    ratingLevel: number;
    ratingName: string;
    idproduct: string;


    constructor ( ratingText: string,  ratingLevel: number, ratingName: string, idproduct: string) {

        this.ratingText = ratingText;
        this.ratingName = ratingName;
        this.ratingLevel = ratingLevel;
        this.idproduct = idproduct;

    }

}