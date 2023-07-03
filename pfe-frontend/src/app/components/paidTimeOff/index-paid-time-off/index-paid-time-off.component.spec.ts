import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexPaidTimeOffComponent } from './index-paid-time-off.component';

describe('IndexPaidTimeOffComponent', () => {
  let component: IndexPaidTimeOffComponent;
  let fixture: ComponentFixture<IndexPaidTimeOffComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexPaidTimeOffComponent]
    });
    fixture = TestBed.createComponent(IndexPaidTimeOffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
