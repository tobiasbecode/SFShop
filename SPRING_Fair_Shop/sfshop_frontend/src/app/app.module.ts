// General
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injectable, APP_INITIALIZER } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FooterComponent } from './footer/footer.component';

// Root
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';

// Authorization
import { AuthComponent } from './auth/auth.component';

// Home
import { HomeComponent } from './home/home.component';
import { SliderComponent } from './home/slider/slider.component';
import { ThumbnailsComponent } from './home/thumbnails/thumbnails.component';

// Category
import { CategoryComponent } from './category/category.component';
import { TnProductComponent } from './category/tn-product/tn-product.component';
import { TnItemComponent } from './category/tn-product/tn-item/tn-item.component';
import { FilterComponent } from './category/filter/filter.component';

import { CoffeeComponent } from './category/coffee/coffee.component';
import { HoneyComponent } from './category/honey/honey.component';
import { TeeComponent } from './category/tee/tee.component';
import { ChocolateComponent } from './category/chocolate/chocolate.component';

// Product
import { ProductComponent } from './product/product.component';
import { ProductNewComponent } from './product/product-new/product-new.component';
import { ProductItemComponent } from './product/product-item/product-item.component';
import { ProductOpinionsComponent } from './product/product-opinions/product-opinions.component';

// Shopping Cart
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { OverviewComponent } from './shopping-cart/overview/overview.component';
import { AddressComponent } from './shopping-cart/address/address.component';
import { BuysuccessComponent } from './shopping-cart/buysuccess/buysuccess.component';

// Services and Directives
import { RatingstarsComponent } from './shared/ratingstars/ratingstars.component';
import { SpringBootProducts } from './services/springbootProducts.service';
import { HighlightDirective } from './directives/highlight.directive';
import { AuthService } from './auth/auth.service';
import { CategoryPipe } from './shared/filter/category.pipe';
import { FilterPipeModule } from 'ngx-filter-pipe';
import { PriceMinPipe } from './shared/filter/pricemin.pipe';
import { PriceMaxPipe } from './shared/filter/pricemax.pipe';
import { Ng5SliderModule } from 'ng5-slider';
import { ProductidService } from './services/productid.service';
import { RatingService } from './services/rating.service';
import { LoadingSpinnerComponent } from './shared/loadingspinner/loading-spinner.component';
import { RatingFilterPipe } from './shared/filter/rating.pipe';
import { ImpressumComponent } from './footer/impressum/impressum.component';


// Get Rating on App Initialization
export function ratingProviderFactory(provider: RatingService) {
  return () => provider.getRating();
}

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },

  { path: 'home', component: HomeComponent },
  { path: 'auth', component: AuthComponent },
  { path: 'impressum', component: ImpressumComponent },

  { path: 'categories', component: CategoryComponent },
  { path: 'categories/coffee', component: CoffeeComponent },
  { path: 'categories/honey', component: HoneyComponent },
  { path: 'categories/chocolate', component: ChocolateComponent },
  { path: 'categories/tee', component: TeeComponent },

  { path: 'products', component: ProductComponent },
  { path: 'product', component: ProductItemComponent },
  { path: 'product/:id', component: ProductItemComponent },

  { path: 'shopping-cart', component: ShoppingCartComponent },
  { path: 'buy-success', component: BuysuccessComponent },

  { path: 'product-new', component: ProductNewComponent },

];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    SliderComponent,
    ThumbnailsComponent,
    CategoryComponent,
    ProductComponent,
    ShoppingCartComponent,
    TnProductComponent,
    FilterComponent,
    CoffeeComponent,
    HoneyComponent,
    TeeComponent,
    ChocolateComponent,
    HighlightDirective,
    TnItemComponent,
    ProductItemComponent,
    RatingstarsComponent,
    ProductOpinionsComponent,
    OverviewComponent,
    AddressComponent,
    ProductNewComponent,
    CategoryPipe,
    PriceMinPipe,
    PriceMaxPipe,
    RatingFilterPipe,
    AuthComponent,
    LoadingSpinnerComponent,
    BuysuccessComponent,
    FooterComponent,
    ImpressumComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    FilterPipeModule,
    HttpClientXsrfModule,
    Ng5SliderModule

  ],
  providers:

    [SpringBootProducts, ProductidService, AuthService, RatingService,
      { provide: APP_INITIALIZER, useFactory: ratingProviderFactory, deps: [RatingService], multi: true }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
