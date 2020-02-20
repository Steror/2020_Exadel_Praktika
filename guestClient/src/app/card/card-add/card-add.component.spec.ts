import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardAddComponent } from './card-add.component';

describe('CardAddComponent', () => {
  let component: CardAddComponent;
  let fixture: ComponentFixture<CardAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
