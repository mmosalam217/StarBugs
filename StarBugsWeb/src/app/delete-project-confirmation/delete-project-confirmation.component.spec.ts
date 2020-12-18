import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteProjectConfirmationComponent } from './delete-project-confirmation.component';

describe('DeleteProjectConfirmationComponent', () => {
  let component: DeleteProjectConfirmationComponent;
  let fixture: ComponentFixture<DeleteProjectConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteProjectConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteProjectConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
