import { Pipe, PipeTransform } from '@angular/core';
import { filter } from 'rxjs/operators';

@Pipe({
  name: 'priceMax',

})
export class PriceMaxPipe implements PipeTransform {

  transform(value: any, filterNumber: number): any {

  
    if (value instanceof Array) {
      return value.filter(value => value.price <= filterNumber);
    }
  }
}
