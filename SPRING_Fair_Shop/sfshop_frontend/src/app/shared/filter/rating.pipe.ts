import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'rating',
  pure: false

})
export class RatingFilterPipe implements PipeTransform {

  transform(value: any, filterNumber: number): any {
    if (value instanceof Array) {
      return value.filter(value => value.ratingLevel >= filterNumber);

    }
  }
}
