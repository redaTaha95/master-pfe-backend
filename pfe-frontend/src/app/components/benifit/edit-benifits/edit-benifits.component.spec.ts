import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBenifitsComponent } from './edit-benifits.component';

describe('EditBenifitsComponent', () => {
  let component: EditBenifitsComponent;
  let fixture: ComponentFixture<EditBenifitsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditBenifitsComponent]
    });
    fixture = TestBed.createComponent(EditBenifitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
