import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'category'
})
export class CategoryPipe implements PipeTransform {

  transform(values: any[], args?: any): any {
    return values.filter((item) => item.category('coffee'));
  }

}
