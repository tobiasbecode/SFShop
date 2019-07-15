import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { Product } from '../../shared/product.model';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from '../../services/order.service';
import { ThumbnailsComponent } from 'src/app/home/thumbnails/thumbnails.component';
import { SessionService } from '../../services/session.service';
import { VirtualTimeScheduler, Subscription } from 'rxjs';
import { ThrowStmt } from '@angular/compiler';
import { AuthService } from '../../auth/auth.service';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';
import { List } from 'lodash';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {


  // Authentifizierung
  isAuthenticated = false;
  cartSymbol: string;
  private userSub: Subscription;
  username: string;
  isAdmin = false;
  noProductsInCart = false;


  overviewProduct: Product;
  overviewProductArray: Array<Product> = [];
  sessionProduct: Product;

  productBeans: Array<string> = [];

  amount: number;
  amountSingeBeforeRemoval: number;

  indexProduct: number;


  priceTotal: number;
  priceSingleBeforeRemoval: number;
  priceTotalBeforeRemoval: number;
  priceTotalWithAmount: number;

  shippingCostsTotal: number;
  shippingCostsTotalBeforeRemoval: number;

  totalCosts: number;


  constructor(private cartService: CartService, private sessionService: SessionService,
              private authService: AuthService, private router: Router, private orderService: OrderService
  ) {
  }

  headElements = ['', 'Artikelnummer', 'Produkt', 'Preis', 'Menge', 'Versandkosten', 'Löschen'];
  headElementsGesamt = ['Produktkosten', 'Versandkosten	', '	Gesamtpreis', '', ''];


  ngOnInit() {


    // user check
    this.userSub = this.authService.user.subscribe(user => {
      this.isAuthenticated = !!user;

      this.isAdmin = !!user && user.username === 'admin';

      this.username = user.username;

    });

    this.checkLogin();



    // Check for Arrays in Session
    if (this.sessionService.getProductCartArrayInSession() !== null) {
      // get amount of session product
      this.sessionProduct = this.sessionService.getProductInSession();
      if (this.sessionProduct === null) {
        this.amount = 0;
        this.noProductsInCart = true;
      } else {
        this.amount = this.sessionProduct.amount;
      }

      // store array from session store in variable
      this.overviewProductArray = this.sessionService.getProductCartArrayInSession();

      // Sum up price in array
      this.priceTotal = this.overviewProductArray.reduce(function(prev, cur) {
        return prev + cur.price;
      }, 0);


      // store index of transfered product in variable
      this.indexProduct = this.cartService.indexProduct;

      this.priceTotalWithAmount = this.priceTotal * this.amount;
      this.priceTotal = this.priceTotalWithAmount;
      this.priceTotal = Math.round(this.priceTotal * 100) / 100;



      // Sum up shipping costs in array
      this.shippingCostsTotal = this.overviewProductArray.reduce(function(prev, cur) {
        return prev + cur.shippingCosts;
      }, 0);
      this.shippingCostsTotal = Math.round(this.shippingCostsTotal * 100) / 100;

      // Sum up total costs
      this.totalCosts = this.priceTotalWithAmount + this.shippingCostsTotal;
      this.totalCosts = Math.round(this.totalCosts * 100) / 100;


      // If no product is ordered => set values to zero
    } else {

      this.noProductsInCart = true;

      this.priceTotal = 0;
      this.shippingCostsTotal = 0;
      this.totalCosts = 0;
    }

  }




  removeProduct(i: number) {

    // save values of shipping costs, amount and price before removal => for total cost calculation
    this.priceTotalBeforeRemoval = this.priceTotal;
    this.priceTotalBeforeRemoval = Math.round(this.priceTotalBeforeRemoval * 100) / 100;

    this.priceSingleBeforeRemoval = this.overviewProductArray[i].price;
    this.amountSingeBeforeRemoval = this.overviewProductArray[i].amount;

    this.shippingCostsTotalBeforeRemoval = this.overviewProductArray.reduce(function(prev, cur) {
      return prev + cur.shippingCosts;
    }, 0);
    this.shippingCostsTotalBeforeRemoval = Math.round(this.shippingCostsTotalBeforeRemoval * 100) / 100;

    // REMOVE Object
    this.overviewProductArray.splice(i, 1);

    // update price Total after Removal
    this.priceTotal = this.priceTotal - (this.priceSingleBeforeRemoval * this.amountSingeBeforeRemoval);
    this.priceTotal = Math.round(this.priceTotal * 100) / 100;

    // Update shipping costs after Removal
    this.shippingCostsTotal = this.overviewProductArray.reduce(function(prev, cur) {
      return prev + cur.shippingCosts;
    }, 0);
    this.shippingCostsTotal = Math.round(this.shippingCostsTotal * 100) / 100;

    // Update total costs
    this.totalCosts = this.totalCosts - (this.priceTotalBeforeRemoval - this.priceTotal)
      - (this.shippingCostsTotalBeforeRemoval - this.shippingCostsTotal);
    this.totalCosts = Math.round(this.totalCosts * 100) / 100;


    // Hide Empty Shopping Cart
    if (this.overviewProductArray.length === 0) {
      this.noProductsInCart = true;

      // store updated array in session and in service
      this.sessionService.storeProductCartArrayInSession(this.overviewProductArray);
      this.cartService.productCartArray = this.sessionService.getProductCartArrayInSession();

      // Set product in Session to null
      this.sessionService.storeProductInSession(null);
    }

  }


  checkLogin() {
    if ((JSON.parse(sessionStorage.getItem('user')) !== null)) {
      const userSession = JSON.parse(sessionStorage.getItem('user'));

      if (userSession !== null) {
        this.username = userSession.username;
        this.isAuthenticated = !!userSession;
        this.isAdmin = !!userSession && userSession.username === 'admin';
      }
    }
  }

  checkAuth() {
    if (this.isAuthenticated !== true) {
      this.router.navigate(['/auth']);

    } else {


      // Post Request to Order
      const orderToSend = this.overviewProductArray;
      orderToSend.map(function(item) {
        delete item.amount;
        return item;
      });

      this.orderService.postOrder(orderToSend);

      // reset shopping cart and navigate to buy success
      this.overviewProductArray = null;
      this.sessionService.storeProductCartArrayInSession(null);
      this.sessionService.storeProductInSession(null);
      this.cartService.saveProductCartArray(null).subscribe(subs => {});
      this.priceTotal = 0;
      this.shippingCostsTotal = 0;
      this.totalCosts = 0;

      this.router.navigate(['/buy-success']);

    }

  }


}

