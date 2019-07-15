import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../shared/product.model';
import { CartService } from './cart.service';
import { subscribeOn } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

// Custom SessionService to keep up data (for example - selected products) during session

export class SessionService {

  constructor() { }

  storeProductInSession(product: Product) {
    return sessionStorage.setItem('sessionproduct', JSON.stringify(product));
  }


  getProductInSession() {
    const sessionProduct = JSON.parse(sessionStorage.getItem('sessionproduct'));
    return sessionProduct;
  }


  storeProductCartArrayInSession(Array: Product[]) {
    return sessionStorage.setItem('array', JSON.stringify(Array)); {
    }
  }

  getProductCartArrayInSession() {
    return JSON.parse(sessionStorage.getItem('array'));

  }

}
