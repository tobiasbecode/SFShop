import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-thumbnails',
  templateUrl: './thumbnails.component.html',
  styleUrls: ['./thumbnails.component.css']
})
export class ThumbnailsComponent implements OnInit {

  tncoffeeImagePath: string;
  tnhoneyImagePath: string;
  tnchocolateImagePath: string;
  tnteeImagePath: string;

  constructor() {
    this.tncoffeeImagePath = '/assets/images/home/thumbnails/coffee.jpg';
    this.tnhoneyImagePath = '/assets/images/home/thumbnails/honey.jpg';
    this.tnchocolateImagePath = '/assets/images/home/thumbnails/chocolate.jpg';
    this.tnteeImagePath = '/assets/images/home/thumbnails/tee.jpg';
  }

  ngOnInit() {
  }
}
