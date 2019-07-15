import { Injectable } from '@angular/core';
import { Product } from '../shared/product.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

// Custom CartService for Transfer of Product Array between Components

export class CartService {

productCart: Product;
amountCart: number;
productCartArray: Array<Product> = [];
productPrice: number;
productPriceSum: number;

indexProduct: number;

  constructor() {

  }
  public saveProductCartArray(productArray: Product[]): Observable<Product[]> {
    return new Observable(subs => {
      this.productCartArray.splice(0);
      subs.next(productArray);
      subs.complete();
    });
  }



  public getProductCartArray(): Observable<Product[]> {
    return new Observable(subs => {
      subs.next(this.productCartArray);
      subs.complete();
    });
  }


}

