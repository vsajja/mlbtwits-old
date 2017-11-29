import {Component, Input, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-player-card',
  templateUrl: './player-card.component.html',
  styleUrls: ['./player-card.component.scss']
})
export class PlayerCardComponent implements OnInit {
  @Input() player: any;

  constructor(private quoteService: QuoteService) {
  }

  ngOnInit() {
  }

  getPlayerMugshotUrl(mlbPlayerId : string) {
    return this.quoteService.getPlayerMugshotUrl(mlbPlayerId);
  }

  calculateAge(birthday: any) {
    return this.quoteService.calculateAge(birthday);
  }
}
