import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPaidTimeOffComponent } from './edit-paid-time-off.component';

describe('EditPaidTimeOffComponent', () => {
  let component: EditPaidTimeOffComponent;
  let fixture: ComponentFixture<EditPaidTimeOffComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditPaidTimeOffComponent]
    });
    fixture = TestBed.createComponent(EditPaidTimeOffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
