import { Component, OnInit, EventEmitter, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Product } from '../../shared/product.model';
import { SpringBootProducts } from '../../services/springbootProducts.service';


@Component({
  selector: 'app-product-new',
  templateUrl: './product-new.component.html',
  styleUrls: ['./product-new.component.css']
})
export class ProductNewComponent implements OnInit {

  // properties

  productListArray: Product[];

  @ViewChild('f') signupForm: NgForm;
  createdProductToSend: Product;
  createdProduct =
    {
      name: '',
      price: '',
      category: '',
      description: '',
      offer: '',
      shippingCosts: '',
      inventory: '',
      imagePath: ''
    };

  submitted = false;
  addTrue = false;

  constructor(private productService: SpringBootProducts) { }


  productsChanged = new EventEmitter<Product[]>();

  ngOnInit() {
    this.productService.getProducts().subscribe(products => {
      this.productListArray = products;
    });

  }

  // new Product - based on Product Model of sfshop-products
  onAddProduct(form: NgForm) {
    this.submitted = true;

    this.createdProduct.name = this.signupForm.value.newProduct.name;
    this.createdProduct.price = this.signupForm.value.newProduct.price;
    this.createdProduct.category = this.signupForm.value.newProduct.category;
    this.createdProduct.description = this.signupForm.value.newProduct.description;
    this.createdProduct.offer = this.signupForm.value.newProduct.offer;
    this.createdProduct.shippingCosts = this.signupForm.value.newProduct.shippingCosts;
    this.createdProduct.inventory = this.signupForm.value.newProduct.inventory;
    this.createdProduct.imagePath = this.signupForm.value.newProduct.imagePath;

    const value = form.value.newProduct;

    this.createdProductToSend = new Product(
      value.name, value.price,
      value.category, value.description,
      value.offer, value.shippingCosts, value.inventory,
      value.id, value.uuid, value.imagePath, value.amount);

  }


  // Send Post-Request with newly created product
  addProduct() {
    this.productService.storeProducts(this.createdProductToSend).subscribe(data => {
      this.productService.getProducts().subscribe(products => {
        this.productListArray = products;
      });

    });
  }

  back() {
    this.submitted = false;
    this.addTrue = false;
  }

  // Delete-Request for Product
  onDelete(i: number) {
    this.productService.deleteProduct(this.productListArray[i].id).subscribe(product => {
      this.productService.getProducts().subscribe(products => {
        this.productListArray = products;
      });
    });
  }

  onAddProductTrue() {
    this.addTrue = true;
  }

}
