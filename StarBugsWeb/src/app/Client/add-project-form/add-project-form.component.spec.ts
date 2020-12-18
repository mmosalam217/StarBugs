import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProjectFormComponent } from './add-project-form.component';

describe('AddProjectFormComponent', () => {
  let component: AddProjectFormComponent;
  let fixture: ComponentFixture<AddProjectFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProjectFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProjectFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
