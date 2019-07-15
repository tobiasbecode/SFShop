import { Component, EventEmitter, OnInit, Input, Output } from '@angular/core';
import { SpringBootProducts } from '../../../services/springbootProducts.service';
import { Product } from '../../../shared/product.model';
import { ProductidService } from '../../../services/productid.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CartService } from 'src/app/services/cart.service';
import { HttpClient } from '@angular/common/http';
import { Rating } from 'src/app/shared/rating.model';
import { RatingService } from 'src/app/services/rating.service';

@Component({
  selector: 'app-tn-item',
  templateUrl: './tn-item.component.html',
  styleUrls: ['./tn-item.component.css']
})
export class TnItemComponent implements OnInit {


  @Input() product: Product;
  idProduct: number;

  productId: number;
  productWithId: Product;
  productWithIdTransfer: Product;
  amount: 1;
  amountForm: FormGroup;
  offerCheck: boolean;

  ratingArrayProductTnItem: Array<Rating> = [];
  ratingLevel: number;


  constructor(private router: Router, private idservice: ProductidService,
              private cartService: CartService, private httpClient: HttpClient,
              private route: ActivatedRoute, public ratingService: RatingService) {

  }

  ngOnInit() {
    this.idservice.idEmit = this.product.id;
    this.idservice.UUIDemit = this.product.uuid;
    this.offerCheck = this.product.offer;
  }


  // On selected Product - Product id is passed to idservice
  onClick() {
    this.idservice.idEmit = this.product.id;
    this.idservice.UUIDemit = this.product.uuid;
    this.router.navigate(['/product/' + this.product.id], { fragment: this.product.uuid });
  }



}
