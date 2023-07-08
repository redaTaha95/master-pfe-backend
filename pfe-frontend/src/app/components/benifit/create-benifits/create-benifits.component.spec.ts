import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBenifitsComponent } from './create-benifits.component';

describe('CreateBenifitsComponent', () => {
  let component: CreateBenifitsComponent;
  let fixture: ComponentFixture<CreateBenifitsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateBenifitsComponent]
    });
    fixture = TestBed.createComponent(CreateBenifitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
