import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-player-hitting-stats',
  templateUrl: './player-hitting-stats.component.html',
  styleUrls: ['./player-hitting-stats.component.scss']
})
export class PlayerHittingStatsComponent implements OnInit {
  @Input() player: any;

  constructor() { }

  ngOnInit() {
  }

}
