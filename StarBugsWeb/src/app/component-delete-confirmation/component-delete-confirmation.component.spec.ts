import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComponentDeleteConfirmationComponent } from './component-delete-confirmation.component';

describe('ComponentDeleteConfirmationComponent', () => {
  let component: ComponentDeleteConfirmationComponent;
  let fixture: ComponentFixture<ComponentDeleteConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComponentDeleteConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComponentDeleteConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
