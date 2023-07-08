import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPayrollComponent } from './edit-payroll.component';

describe('EditPayrollComponent', () => {
  let component: EditPayrollComponent;
  let fixture: ComponentFixture<EditPayrollComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditPayrollComponent]
    });
    fixture = TestBed.createComponent(EditPayrollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
