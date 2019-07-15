import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../shared/product.model';
import { Observable, of, Subscriber } from 'rxjs';
import { map, catchError, tap, take, exhaustMap } from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';

@Injectable()

// Custom ProductsService for Commuication (CRUD-Operations) with sfshop-products

export class SpringBootProducts {

  token: string;

  constructor(private authService: AuthService, private httpClient: HttpClient) {
    this.authService.user.subscribe(token => {
      this.token = token._token;
    });
  }

  storeProducts(product: Product): Observable<any> {
    return new Observable(subscriber => {


      this.authService.user.subscribe(token => {
        this.token = token._token;
      });


      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer + ""' + this.token
      });


      this.httpClient.post('/api/pservice/products/new', product,
        { headers })

        .subscribe(data => {
          subscriber.next(data);
        });
    });
  }


  getProducts() {
    return new Observable<Product[]>(subs => {

      return this.httpClient.get<Product[]>('/api/pservice/products/list')

        .subscribe(data => {
          subs.next(data);
        });

    });
  }

  deleteProduct(productId: number): Observable<any> {
    return new Observable(subscriber => {

      console.log(this.token);

      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer + ""' + this.token
      });
      this.httpClient.delete('/api/pservice/products/delete/' + productId,
        { headers })

        .subscribe(data => {
          subscriber.next(data);
        });
    });
  }
}

