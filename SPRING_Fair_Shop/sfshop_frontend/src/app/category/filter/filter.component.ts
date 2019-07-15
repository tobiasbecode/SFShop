import { Component, EventEmitter, OnInit, HostListener, Output, Input } from '@angular/core';
import { Options } from 'ng5-slider';


@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {

// Price Filter

minValue = 0;
maxValue = 10;

  priceMin: number;
  priceMax: number;

  optionsPrice: Options = {
    floor: 0,
    ceil: 40,
    translate: (value: number): string => {
      return '€' + value;
    },

    combineLabels: (minValue: string, maxValue: string): string => {
      return 'von ' + minValue + ' bis ' + maxValue;
    }
  };

  @Output() priceEmitMin: EventEmitter<number> = new EventEmitter();
  @Output() priceEmitMax: EventEmitter<number> = new EventEmitter();
  @Output() ratingEmitMin: EventEmitter<number> = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  updatePriceMin(value) {
    this.priceMin = value;
    this.priceEmitMin.emit(this.priceMin);

  }
  updatePriceMax(value) {
      this.priceMax = value;
      this.priceEmitMax.emit(this.priceMax);
    }

  }

