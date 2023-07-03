import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBenifitTypeComponent } from './create-benifit-type.component';

describe('CreateBenifitTypeComponent', () => {
  let component: CreateBenifitTypeComponent;
  let fixture: ComponentFixture<CreateBenifitTypeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateBenifitTypeComponent]
    });
    fixture = TestBed.createComponent(CreateBenifitTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
