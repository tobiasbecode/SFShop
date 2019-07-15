import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TnProductComponent } from './tn-product.component';

describe('TnProductComponent', () => {
  let component: TnProductComponent;
  let fixture: ComponentFixture<TnProductComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TnProductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TnProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
