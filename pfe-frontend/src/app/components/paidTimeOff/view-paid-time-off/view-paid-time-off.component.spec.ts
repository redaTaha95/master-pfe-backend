import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPaidTimeOffComponent } from './view-paid-time-off.component';

describe('ViewPaidTimeOffComponent', () => {
  let component: ViewPaidTimeOffComponent;
  let fixture: ComponentFixture<ViewPaidTimeOffComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewPaidTimeOffComponent]
    });
    fixture = TestBed.createComponent(ViewPaidTimeOffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
