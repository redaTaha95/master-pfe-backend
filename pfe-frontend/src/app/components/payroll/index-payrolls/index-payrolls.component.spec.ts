import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexPayrollsComponent } from './index-payrolls.component';

describe('IndexPayrollsComponent', () => {
  let component: IndexPayrollsComponent;
  let fixture: ComponentFixture<IndexPayrollsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexPayrollsComponent]
    });
    fixture = TestBed.createComponent(IndexPayrollsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
