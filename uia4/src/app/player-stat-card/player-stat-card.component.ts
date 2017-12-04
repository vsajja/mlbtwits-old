import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-player-stat-card',
  templateUrl: './player-stat-card.component.html',
  styleUrls: ['./player-stat-card.component.scss']
})
export class PlayerStatCardComponent implements OnInit {
  @Input() player: any;

  constructor() { }

  ngOnInit() {
  }
}
