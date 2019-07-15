import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { Address } from 'src/app/shared/address.model';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {

  addressArrayToDisplay: Address [];


  constructor(private orderService: OrderService) {
  }

  headDelivery = ['Name', 'Straße', 'PLZ', 'Stadt', 'Land'];

  ngOnInit() {
    this.addressArrayToDisplay = this.orderService.addressArrayToDisplay;
  }



}
