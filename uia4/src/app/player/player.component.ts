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

  teamId : string;
  mlbPlayerId : string;
  playerName : string;
  playerNamePlain : string;
  mugshotUrl : string;

  constructor(private route: ActivatedRoute, private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.playerId = this.route.snapshot.paramMap.get('playerId');
    this.quoteService.getPlayer(this.playerId).subscribe(
      (res: Response) => {
        let player = res.json();
        // FIXME: setting the player variable only works through a setter?
        this.setPlayer(player);
      }
    );
  }

  public setPlayer(player : any) {
    this.playerName = player.playerName;
    this.playerNamePlain = player.playerNamePlain;
    this.teamId = player.teamId;
    this.mlbPlayerId = player.mlbPlayerId;
    this.mugshotUrl = "http://gdx.mlb.com/images/gameday/mugshots/mlb/" + this.mlbPlayerId + "@4x.jpg";
  }
}
