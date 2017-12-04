import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerStatcardComponent } from './player-statcard.component';

describe('PlayerStatcardComponent', () => {
  let component: PlayerStatcardComponent;
  let fixture: ComponentFixture<PlayerStatcardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlayerStatcardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlayerStatcardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
