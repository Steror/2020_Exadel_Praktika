import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardUpdateComponent } from './card-update.component';

describe('CardUpdateComponent', () => {
  let component: CardUpdateComponent;
  let fixture: ComponentFixture<CardUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
