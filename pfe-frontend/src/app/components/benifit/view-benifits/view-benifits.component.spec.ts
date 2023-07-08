import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBenifitsComponent } from './view-benifits.component';

describe('ViewBenifitsComponent', () => {
  let component: ViewBenifitsComponent;
  let fixture: ComponentFixture<ViewBenifitsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewBenifitsComponent]
    });
    fixture = TestBed.createComponent(ViewBenifitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
