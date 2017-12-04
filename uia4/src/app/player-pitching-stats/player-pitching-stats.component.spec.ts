import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerPitchingStatsComponent } from './player-pitching-stats.component';

describe('PlayerPitchingStatsComponent', () => {
  let component: PlayerPitchingStatsComponent;
  let fixture: ComponentFixture<PlayerPitchingStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlayerPitchingStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlayerPitchingStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
