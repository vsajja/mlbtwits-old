import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss']
})
export class PlayerComponent implements OnInit {
  playerId: string;
  player: any;
  playerTweets: any;
  playerStats: any;

  constructor(private route: ActivatedRoute, private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.playerId = this.route.snapshot.paramMap.get('playerId');

    this.getPlayer(this.playerId);
    this.getPlayerTweets(this.playerId);
    this.getPlayerStats(this.playerId);
  }

  getPlayer(playerId: string) {
    this.quoteService.getPlayer(playerId).subscribe(
      (data: any) => {
        this.player = data;
      });
  }

  getPlayerTweets(playerId: string) {
    this.quoteService.getPlayerTweets(playerId).subscribe(
      (data: any) => {
        this.playerTweets = data;
      });
  }

  getPlayerStats(playerId: string) {
    this.quoteService.getPlayerStats(playerId).subscribe(
      (data: any) => {
        this.playerStats = data;
        console.log(this.playerStats);
      });
  }

  FIXME_timeAgo(value: string) {
    return this.quoteService.FIXME_timeAgo(value);
  }
}
