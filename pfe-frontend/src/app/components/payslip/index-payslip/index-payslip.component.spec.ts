import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexPayslipComponent } from './index-payslip.component';

describe('IndexPayslipComponent', () => {
  let component: IndexPayslipComponent;
  let fixture: ComponentFixture<IndexPayslipComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexPayslipComponent]
    });
    fixture = TestBed.createComponent(IndexPayslipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
