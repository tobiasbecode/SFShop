import { Injectable } from '@angular/core';
import { Product } from '../shared/product.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';
import { Address } from '../shared/address.model';

@Injectable({
  providedIn: 'root'
})

// Custom OrderService for Commuication with sfshop-order

export class OrderService {

  serviceOrderProduct: Product;
  token: string;
  addressArray: Address[];
  addressArrayToDisplay: Address [];

  constructor(private httpClient: HttpClient, private authService: AuthService) {
    this.authService.user.subscribe(token => {
      this.token = token._token;
    });
  }


  public postOrder(order: Product[]) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    const orderToSend = JSON.parse(JSON.stringify(order));
    this.httpClient.post('/api/oservice/orders', orderToSend, { headers }).subscribe(obj => { });
  }



  public postAddress(address: any) {
    return new Observable<any>(subscriber => {


      this.authService.user.subscribe(token => {
        this.token = token._token;
      });


      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer + ""' + this.token
      });

      const addressToSend = JSON.parse(JSON.stringify(address));
      this.httpClient.post('/api/oservice/address/new', addressToSend, { headers })

        .pipe(tap(resData => {
          const orderRes = resData;
          subscriber.next(orderRes);
          subscriber.complete();
        })).subscribe();

    });
  }


  public getAddress(): Observable<Address[]> {
    return new Observable<Address[]>(subscriber => {
      this.authService.user.subscribe(token => {
        this.token = token._token;
      });


      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer + ""' + this.token
      });

      this.httpClient.get<Address[]>('/api/oservice/address/list', { headers })

        .pipe(tap(resData => {
          subscriber.next(resData);
          subscriber.complete();
        })).subscribe();

    });
  }


  public getAddressFromService(): Observable<any> {
    return new Observable(subs => {
      subs.next();
      subs.complete();

    });
  }

}



