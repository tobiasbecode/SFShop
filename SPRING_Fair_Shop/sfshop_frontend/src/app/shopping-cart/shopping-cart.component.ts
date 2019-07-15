import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Product } from '../shared/product.model';
import { OrderService } from '../services/order.service';
import { AuthService } from '../auth/auth.service';
import { Subscription } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Address } from '../shared/address.model';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  productOrder: Product;
  productAmount: number;
  addressForm: FormGroup;
  addressToPost: Address;

  // Authentification
  isAuthenticated = false;
  cartSymbol: string;
  private userSub: Subscription;
  username: string;
  isAdmin = false;
  isHidden = false;
  addressIsSend = false;
  isLoading = false;


  // Check Address on Database
  addressIsFound = false;
  AddressArray: Address[];
  AddressArrayFilter: Address[];
  addressToDelete: Address;

  constructor(private authService: AuthService, private orderService: OrderService) {
  }

  ngOnInit() {


    // user check
    this.userSub = this.authService.user.subscribe(user => {
      this.isAuthenticated = !!user;

      this.isAdmin = !!user && user.username === 'admin';

      this.username = user.username;


    });
    this.checkLogin();


    this.addressForm = new FormGroup({
      addressName: new FormControl(null, Validators.required),
      addressStreet: new FormControl(null, Validators.required),
      addressPLZ: new FormControl(null, Validators.required),
      addressCity: new FormControl(null, Validators.required),
      addressCountry: new FormControl(null, Validators.required),
      addressMail: new FormControl(null, [Validators.email, Validators.required]),
      username: new FormControl(this.username)
    });

  }

  checkLogin() {
    if ((JSON.parse(sessionStorage.getItem('user')) !== null)) {
      const userSession = JSON.parse(sessionStorage.getItem('user'));

      if (userSession !== null) {
        this.username = userSession.username;
        this.isAuthenticated = !!userSession;
        this.isAdmin = !!userSession && userSession.username === 'admin';
      }

      // address check
      // get Request
      this.isLoading = true;

      if (this.isAuthenticated) {
      this.orderService.getAddress().subscribe(data => {

        this.AddressArray = data;

        this.AddressArrayFilter = this.AddressArray.filter(address => {
          this.addressToDelete = address;
          return address.username === this.username;

        });

        this.orderService.addressArrayToDisplay = this.AddressArrayFilter;

        // check Address Array for entries
        if (this.addressToDelete.id === null) {
          this.addressIsFound = false;
          this.isLoading = false;
        }

        if (this.AddressArrayFilter.length === 1) {
          this.addressIsFound = true;
          this.isLoading = false;
        }

        if (this.AddressArrayFilter.length === 0) {
          this.addressIsFound = false;
          this.isLoading = false;
        }

        if (this.AddressArrayFilter.length === null) {
          this.addressIsFound = false;
          this.isLoading = false;
        }

        // Hand over to Service to View in Address Component
        this.orderService.addressArray = this.AddressArrayFilter;

      });


    } else {
      // if address ist not found, display form for new address
      this.addressIsFound = false;
      this.isLoading = false;
    }
  }
  }

  onSubmit() {
    if (this.addressForm.valid) {
      this.addressToPost = this.addressForm.value;
      this.orderService.postAddress(this.addressToPost).subscribe(order => { });
    }
  }

  onSendAddress() {
    if (this.addressForm.valid) {
      this.addressIsSend = true;
      window.location.reload();
    }

  }

}


