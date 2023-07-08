import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePaidTimeOffComponent } from './create-paid-time-off.component';

describe('CreatePaidTimeOffComponent', () => {
  let component: CreatePaidTimeOffComponent;
  let fixture: ComponentFixture<CreatePaidTimeOffComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreatePaidTimeOffComponent]
    });
    fixture = TestBed.createComponent(CreatePaidTimeOffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
