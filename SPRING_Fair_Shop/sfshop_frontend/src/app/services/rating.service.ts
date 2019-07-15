import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Rating } from '../shared/rating.model';
import { tap, subscribeOn } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

// Custom RatingService for Commuication (CRUD-Operations) with sfshop-rating

export class RatingService {

  ratingArray: Rating[];
  ratingArraySecond: Rating[];
  ratingLevelTransfer: number;


  constructor(private httpClient: HttpClient) {

  }

  public getRating() {
    return new Observable<Rating[]>(subscriber => {

      this.httpClient.get<Rating[]>('/api/rservice/ratings/')

        .pipe(tap(resData => {
          const rating = resData;
          subscriber.next(rating);
          subscriber.complete();
        })).subscribe();

    });
  }

  public storeRatingLevel(rating: number) {
    this.ratingLevelTransfer = rating;
  }


  public getRatingLevel(): Observable<number> {
    return new Observable(subs => {
      subs.next(this.ratingLevelTransfer);
      subs.complete();
    });
  }

  public saveRatingToDatabase(rating: Rating) {
    this.httpClient.post('/api/rservice/ratings/new', rating).subscribe(err => {});
  }
}



