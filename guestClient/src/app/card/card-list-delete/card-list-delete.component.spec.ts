import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardListDeleteComponent } from './card-list-delete.component';

describe('CardListDeleteComponent', () => {
  let component: CardListDeleteComponent;
  let fixture: ComponentFixture<CardListDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardListDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardListDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
