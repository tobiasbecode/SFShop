import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChocolateComponent } from './chocolate.component';

describe('ChocolateComponent', () => {
  let component: ChocolateComponent;
  let fixture: ComponentFixture<ChocolateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChocolateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChocolateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
