import { Component, OnInit, Input } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ProductidService } from '../../services/productid.service';
import { Product } from 'src/app/shared/product.model';
import { CartService } from 'src/app/services/cart.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { RatingService } from '../../services/rating.service';
import { Rating } from 'src/app/shared/rating.model';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {

  productId: number;
  productWithId: Product;
  productWithIdTransfer: Product;

  amount: 1;
  amountForm: FormGroup;

  offerCheck: boolean;

  ratingFormboolean: false;

  productArrayForCart: Product[];

  ratingArraySession: Rating[];


  constructor(private router: Router, private idservice: ProductidService,
              private cartService: CartService, private httpClient: HttpClient,
              private activeroute: ActivatedRoute,
              private ratingService: RatingService,
              private sessionService: SessionService) {

    this.productId = this.idservice.idEmit;
    this.productId = this.activeroute.snapshot.params['id'];

    this.idservice.UUIDemit = this.activeroute.snapshot.fragment;

    // Get Product with Id (parsed from url)
    this.httpClient.get<Product>('/api/pservice/products/' + ((this.productId)))
      .subscribe(product => {
        this.productWithId = product;
        this.offerCheck = this.productWithId.offer;
      });


  }


  ngOnInit() {
    this.ratingArraySession = this.ratingService.ratingArray;
    sessionStorage.setItem('ratingArray', JSON.stringify(this.ratingArraySession));

    this.amountForm = new FormGroup({
      'amountinput': new FormControl(1, Validators.min(1)),
    });

    this.amount = 1;

  }


  onSubmit() {
    if (!this.amountForm.get('amountinput').valid) {
      this.amount = 1;
    } else {
      this.amount = this.amountForm.get('amountinput').value;
    }
    if (!this.amount) {
      this.amount = 1;
      this.productWithId.amount = this.amount;
    } else {
      this.productWithId.amount = this.amount;

      // reduce inventory of product with amount
      this.productWithId.inventory = this.productWithId.inventory - this.amount;

    }

    this.router.navigate(['/shopping-cart']);

    // store selected product
    this.sessionService.storeProductInSession(this.productWithId);

    // add product to array in service / store array in session
    this.cartService.getProductCartArray().subscribe(subs => {
    subs.push(this.productWithId);
    this.cartService.indexProduct = this.cartService.productCartArray.indexOf(this.productWithId);
    this.sessionService.storeProductCartArrayInSession(this.cartService.productCartArray);
    this.cartService.productPrice = this.productWithId.price;

    });
  }

}
