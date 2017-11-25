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
  player : any;
  playerTweets : any;
  playerStats: any;

  constructor(private route: ActivatedRoute, private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.playerId = this.route.snapshot.paramMap.get('playerId');

    // get player details
    this.quoteService.getPlayer(this.playerId).subscribe(
      (res: Response) => {
        this.player = res.json();
        this.player.mugshotUrl = "http://gdx.mlb.com/images/gameday/mugshots/mlb/" + this.player.mlbPlayerId + "@4x.jpg";
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
}
