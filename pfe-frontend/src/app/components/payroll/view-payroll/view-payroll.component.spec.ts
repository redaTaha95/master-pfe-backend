import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPayrollComponent } from './view-payroll.component';

describe('ViewPayrollComponent', () => {
  let component: ViewPayrollComponent;
  let fixture: ComponentFixture<ViewPayrollComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewPayrollComponent]
    });
    fixture = TestBed.createComponent(ViewPayrollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
