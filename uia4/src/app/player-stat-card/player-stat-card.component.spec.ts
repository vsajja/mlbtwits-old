import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerStatCardComponent } from './player-stat-card.component';

describe('PlayerStatCardComponent', () => {
  let component: PlayerStatCardComponent;
  let fixture: ComponentFixture<PlayerStatCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlayerStatCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlayerStatCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
