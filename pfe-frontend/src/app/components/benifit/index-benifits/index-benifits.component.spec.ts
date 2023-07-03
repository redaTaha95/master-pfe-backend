import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexBenifitsComponent } from './index-benifits.component';

describe('IndexBenifitsComponent', () => {
  let component: IndexBenifitsComponent;
  let fixture: ComponentFixture<IndexBenifitsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexBenifitsComponent]
    });
    fixture = TestBed.createComponent(IndexBenifitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
