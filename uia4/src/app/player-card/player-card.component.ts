import {Component, Input, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-player-card',
  templateUrl: './player-card.component.html',
  styleUrls: ['./player-card.component.scss']
})
export class PlayerCardComponent implements OnInit {
  @Input() player: any;

  team: any;

  constructor(private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.getTeam(this.player.teamId);
  }

  getTeam(teamId: string) {
    this.quoteService.getTeam(teamId).subscribe( (data: any) => {
      this.team = data;
    });
  }

  getPlayerMugshotUrl(mlbPlayerId : string) {
    return this.quoteService.getPlayerMugshotUrl(mlbPlayerId);
  }

  calculateAge(birthday: any) {
    return this.quoteService.calculateAge(birthday);
  }
}
