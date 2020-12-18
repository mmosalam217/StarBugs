import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComponentCreatorComponent } from './component-creator.component';

describe('ComponentCreatorComponent', () => {
  let component: ComponentCreatorComponent;
  let fixture: ComponentFixture<ComponentCreatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComponentCreatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComponentCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
