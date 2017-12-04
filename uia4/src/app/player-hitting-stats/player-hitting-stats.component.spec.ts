import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerHittingStatsComponent } from './player-hitting-stats.component';

describe('PlayerHittingStatsComponent', () => {
  let component: PlayerHittingStatsComponent;
  let fixture: ComponentFixture<PlayerHittingStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlayerHittingStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlayerHittingStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
