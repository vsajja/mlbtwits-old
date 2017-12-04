import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-player-pitching-stats',
  templateUrl: './player-pitching-stats.component.html',
  styleUrls: ['./player-pitching-stats.component.scss']
})
export class PlayerPitchingStatsComponent implements OnInit {
  @Input() player: any;

  constructor() {
  }

  ngOnInit() {
  }

}
