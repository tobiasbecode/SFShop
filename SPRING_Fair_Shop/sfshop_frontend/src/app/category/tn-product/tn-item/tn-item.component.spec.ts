import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TnItemComponent } from './tn-item.component';

describe('TnItemComponent', () => {
  let component: TnItemComponent;
  let fixture: ComponentFixture<TnItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TnItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TnItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
