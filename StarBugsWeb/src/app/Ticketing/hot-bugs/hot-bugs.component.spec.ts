import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HotBugsComponent } from './hot-bugs.component';

describe('HotBugsComponent', () => {
  let component: HotBugsComponent;
  let fixture: ComponentFixture<HotBugsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HotBugsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HotBugsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
