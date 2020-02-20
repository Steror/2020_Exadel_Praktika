import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeSelectlistComponent } from './type-selectlist.component';

describe('TypeSelectlistComponent', () => {
  let component: TypeSelectlistComponent;
  let fixture: ComponentFixture<TypeSelectlistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TypeSelectlistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeSelectlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
