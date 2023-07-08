import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectEmployeePaidTimeOffComponent } from './select-employee-paid-time-off.component';

describe('SelectEmployeePaidTimeOffComponent', () => {
  let component: SelectEmployeePaidTimeOffComponent;
  let fixture: ComponentFixture<SelectEmployeePaidTimeOffComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SelectEmployeePaidTimeOffComponent]
    });
    fixture = TestBed.createComponent(SelectEmployeePaidTimeOffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
