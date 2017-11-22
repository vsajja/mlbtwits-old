import { Component, OnInit } from '@angular/core';
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss']
})
export class PlayersComponent implements OnInit {
  players: any;

  constructor(private quoteService: QuoteService) { }

  ngOnInit() {
    this.quoteService.getPlayers()
      .subscribe((res: Response) => {
        this.players = res.json();
      }
    );
  }
}
