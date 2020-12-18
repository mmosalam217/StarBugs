import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketAssignmentDialogComponent } from './ticket-assignment-dialog.component';

describe('TicketAssignmentDialogComponent', () => {
  let component: TicketAssignmentDialogComponent;
  let fixture: ComponentFixture<TicketAssignmentDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicketAssignmentDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketAssignmentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
