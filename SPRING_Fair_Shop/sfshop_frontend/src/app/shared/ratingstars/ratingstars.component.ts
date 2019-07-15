import { Component, OnInit, Input } from '@angular/core';
import { RatingService } from 'src/app/services/rating.service';
import { ProductidService } from 'src/app/services/productid.service';
import { Rating } from '../rating.model';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-ratingstars',
  templateUrl: './ratingstars.component.html',
  styleUrls: ['./ratingstars.component.css']
})
export class RatingstarsComponent implements OnInit {


  productUUID: any;
  ratingArrayProduct: Array<Rating> = [];
  ratingArrayProductFilter: Array<Rating> = [];
  ratingLevel: number;

  ratingArrayFromObservable: any;

  constructor(private ratingService: RatingService,
              private idservice: ProductidService, private httpClient: HttpClient) {

  }
  ngOnInit() {
    this.productUUID = this.idservice.UUIDemit;

    // Get Rating Array from Observable and filter with product id
    this.ratingService.getRating().subscribe(resData => {


      this.ratingArrayProduct = resData;

      this.ratingArrayProductFilter = this.ratingArrayProduct.filter((rating: Rating) => rating.idproduct === this.productUUID);

      // reduced ratingLevel from Rating Array - mean value calculation for rating stars
      this.ratingLevel = this.ratingArrayProductFilter.reduce(function(prev, cur) {
        return prev + cur.ratingLevel;
      }, 0);
      this.ratingLevel = Math.round(this.ratingLevel / this.ratingArrayProductFilter.length);
      this.ratingService.storeRatingLevel(this.ratingLevel);

    }
    );
  }
}
