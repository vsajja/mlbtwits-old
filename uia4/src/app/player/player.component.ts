import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuoteService} from "../services/quote.service";
import {environment} from "../../environments/environment";

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

    // get player details
    this.quoteService.getPlayer(this.playerId).subscribe(
      (res: Response) => {
        this.player = res.json();
      }
    );

    // get player tweets
    this.quoteService.getPlayerTweets(this.playerId).subscribe(
      (res: Response) => {
        this.playerTweets = res.json();
      }
    );

    // get hitting stats
    this.quoteService.getPlayerStats(this.playerId).subscribe(
      (res: Response) => {
        this.playerStats = res.json();
      }
    );
  }

  FIXME_timeAgo(value: string) {
    return this.quoteService.FIXME_timeAgo(value);
  }

  getPlayerMugshotUrl(mlbPlayerId: string) {
    return this.quoteService.getPlayerMugshotUrl(mlbPlayerId);
  }
}
