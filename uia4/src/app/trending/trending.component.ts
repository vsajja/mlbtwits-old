import {Component, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-trending',
  templateUrl: './trending.component.html',
  styleUrls: ['./trending.component.scss']
})
export class TrendingComponent implements OnInit {
  players: any;
  playerCount: any;
  teamCount: any;
  userCount: any;
  isLoading: boolean;

  constructor(private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.getTrending();
  }

  getTrending() {
    this.isLoading = true;
    this.quoteService.getMlbTwits()
      .finally(() => {
        this.isLoading = false;
      })
      .subscribe((mlbtwits: any) => {
        this.players = mlbtwits.trending;
        this.playerCount = mlbtwits.players;
        this.teamCount = mlbtwits.teams;
        this.userCount = mlbtwits.users;
      });
  }
}
