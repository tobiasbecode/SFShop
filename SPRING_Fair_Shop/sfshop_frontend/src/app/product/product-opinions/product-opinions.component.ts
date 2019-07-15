import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, NgForm, Validators } from '@angular/forms';
import { RatingService } from 'src/app/services/rating.service';
import { Rating } from 'src/app/shared/rating.model';
import { ProductidService } from 'src/app/services/productid.service';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-product-opinions',
  templateUrl: './product-opinions.component.html',
  styleUrls: ['./product-opinions.component.css']
})
export class ProductOpinionsComponent implements OnInit {


  isAuthenticated = false;
  cartSymbol: string;
  private userSub: Subscription;
  username: string;
  isAdmin = false;
  noProductsInCart = false;


  productUUID: string;
  ratingForm: FormGroup;
  ratingToPost: Rating;

  definedValue: Rating[];

  ratingStars: number[] = [0, 1, 2, 3, 4, 5];

  ratingArrayProduct: Rating[];

  constructor(private ratingService: RatingService, private idservice: ProductidService,
              private httpClient: HttpClient, private r: ActivatedRoute, private authService: AuthService,
              private router: Router) {

  }

  setRating = false;

  ngOnInit() {


    // user check
    this.userSub = this.authService.user.subscribe(user => {
      this.isAuthenticated = !!user;

      this.isAdmin = !!user && user.username === 'admin';

      this.username = user.username;

    });

    this.checkLogin();

    // Initialize Rating Form
    this.ratingForm = new FormGroup({
      ratingName: new FormControl(),
      ratingText: new FormControl(),
      ratingLevel: new FormControl()
    });

    // Get UUID
    this.productUUID = this.idservice.UUIDemit;

    // Get Rating Array from Observable
    this.ratingService.getRating().subscribe(resData => {

      this.ratingArrayProduct = resData;
      this.ratingArrayProduct = this.ratingArrayProduct.filter(rating => rating.idproduct === this.productUUID);
    }
    );

  }

  onRating() {
    if (this.isAuthenticated) {
      this.setRating = true;
    } else {
      this.router.navigate(['/auth']);
    }

  }

  // Submitted Rating is send to sfshop-rating
  onSubmit() {
    this.ratingToPost = this.ratingForm.value;
    this.ratingToPost.idproduct = this.productUUID;

    this.ratingService.saveRatingToDatabase(this.ratingToPost);

    // reload ratings
    this.ratingArrayProduct.push(this.ratingToPost);

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

}
