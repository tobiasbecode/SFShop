import { Injectable, EventEmitter, Output } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProductidService {
  idEmit: number;
  UUIDemit: string;

  constructor() { }
}
