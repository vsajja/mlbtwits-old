import {Component, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss']
})
export class PlayersComponent implements OnInit {
  players: any;
  rows: any = [];

  constructor(private quoteService: QuoteService) {
    this.refreshPlayers();
  }

  ngOnInit() {
  }

  searchPlayers(term: string) {
    // FIXME: try not to load all players
    this.refreshPlayers();
    if(term) {
      this.players = this.players.filter(
        (player: any) => player.playerNamePlain.toLowerCase().indexOf(term.toLowerCase()) > -1
      )
    }
  }

  refreshPlayers() {
    this.quoteService.getPlayers()
      .subscribe((res: Response) => {
        this.players = res.json();
        this.players = this.players.filter((player: any) => player.mlbPlayerId != null);
        this.rows = this.players
      });
  }

  getPlayerMugshotUrl(mlbPlayerId : string) {
    return this.quoteService.getPlayerMugshotUrl(mlbPlayerId);
  }
}
