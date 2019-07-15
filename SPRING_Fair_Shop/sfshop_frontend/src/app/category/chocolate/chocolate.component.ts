import { Component, OnInit } from '@angular/core';
import { SpringBootProducts } from 'src/app/services/springbootProducts.service';
import { Product } from 'src/app/shared/product.model';
import { map } from 'rxjs/operators';
import { RatingService } from '../../services/rating.service';

@Component({
  selector: 'app-chocolate',
  templateUrl: './chocolate.component.html',
  styleUrls: ['./chocolate.component.css']
})
export class ChocolateComponent implements OnInit {

  productsArray: Product[];
  productsArrayHttp: Product[];

  receivePriceMin: number = 0;
  receivePriceMax: number = 1000;

  constructor(public rest: SpringBootProducts, public ratingService: RatingService) {
  }

  ngOnInit() {
    this.getProducts();
  }

  // PriceFilter Methods
  priceMessageMin(message: number): void {
    this.receivePriceMin = message;

  }
  priceMessageMax(message: number): void {
      this.receivePriceMax = message;
  }

  // get products from database => filter by category
  getProducts() {
    this.rest.getProducts()
      .pipe(
        map(data => {
          const responseArray = [];
          for (const key in data) {
            if (data.hasOwnProperty(key)) {
              {
                responseArray.push(data[key]);
              }
            }
          }

          this.productsArrayHttp = responseArray;
          this.productsArray = this.productsArrayHttp.filter(function(product) {
            return product.category === 'chocolate';
          });
        })
      )
      .subscribe(products => {
      });
  }
}
