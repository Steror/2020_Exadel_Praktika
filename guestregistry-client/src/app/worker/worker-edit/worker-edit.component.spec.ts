import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkerEditComponent } from './worker-edit.component';

describe('WorkerEditComponent', () => {
  let component: WorkerEditComponent;
  let fixture: ComponentFixture<WorkerEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkerEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkerEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
