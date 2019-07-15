import { Component, OnInit, Input, Pipe } from '@angular/core';
import { SpringBootProducts } from 'src/app/services/springbootProducts.service';
import { Product } from '../../shared/product.model';
import { map} from 'rxjs/operators';


@Component({
  selector: 'app-coffee',
  templateUrl: './coffee.component.html',
  styleUrls: ['./coffee.component.css']
})
export class CoffeeComponent implements OnInit {

  productsArray: Product[];
  productsArrayHttp: Product[];

  productArrayforRating: Product[];

  receivePriceMin: number = 0;
  receivePriceMax: number = 1000;

  ratingLevelInput: number;

  constructor(public rest: SpringBootProducts) {
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
            return product.category === 'coffee';

          });
        })
      )
      .subscribe(products => {
      });
  }

}
