import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.css']
})
export class SliderComponent implements OnInit {

  coffeeImagePath: string;
  honeyImagePath: string;
  chocolateImagePath: string;

  constructor() {
    this.coffeeImagePath = '/assets/images/home/slider/slider-coffee.jpg';
    this.honeyImagePath = '/assets/images/home/slider/slider-honey.jpg';
    this.chocolateImagePath = '/assets/images/home/slider/slider-chocolate.jpg';
  }

  ngOnInit() {
  }
}
