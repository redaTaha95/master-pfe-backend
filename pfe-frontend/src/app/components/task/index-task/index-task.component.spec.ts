import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexTaskComponent } from './index-task.component';

describe('IndexTaskComponent', () => {
  let component: IndexTaskComponent;
  let fixture: ComponentFixture<IndexTaskComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndexTaskComponent]
    });
    fixture = TestBed.createComponent(IndexTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
