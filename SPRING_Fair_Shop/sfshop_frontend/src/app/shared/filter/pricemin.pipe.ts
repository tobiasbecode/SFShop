import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'priceMin',
  pure: false

})
export class PriceMinPipe implements PipeTransform {

  transform(value: any, filterNumber: number): any {

  
    if (value instanceof Array) {
      return value.filter(value => value.price >= filterNumber);

    }
  }
}