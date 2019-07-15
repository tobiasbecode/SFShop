import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductOpinionsComponent } from './product-opinions.component';

describe('ProductOpinionsComponent', () => {
  let component: ProductOpinionsComponent;
  let fixture: ComponentFixture<ProductOpinionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductOpinionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductOpinionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
