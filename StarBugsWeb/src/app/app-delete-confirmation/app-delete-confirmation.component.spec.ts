import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppDeleteConfirmationComponent } from './app-delete-confirmation.component';

describe('AppDeleteConfirmationComponent', () => {
  let component: AppDeleteConfirmationComponent;
  let fixture: ComponentFixture<AppDeleteConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppDeleteConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppDeleteConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
