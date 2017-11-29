import {Component, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";
import {PagerService} from "../services/pager.service";

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss']
})
export class PlayersComponent implements OnInit {
  players: any;

  private allItems: any[];
  pager: any = {};
  pagedItems: any[];
  page = 1;

  constructor(private quoteService: QuoteService, private pagerService: PagerService) {
  }

  ngOnInit() {
    this.refreshPlayers(null);
  }

  searchPlayers(term: string) {
    this.refreshPlayers(term);
  }

  refreshPlayers(term: string) {
    this.quoteService.getPlayers()
      .subscribe((data: any) => {
        // FIXME - get all players with a mlbPlayerId
        this.players = data.filter((player: any) => player.mlbPlayerId != null);
        this.allItems = this.players;

        if(term != null) {
          this.allItems = this.allItems.filter(
            (player: any) => player.playerNamePlain.toLowerCase().indexOf(term.toLowerCase()) > -1
          );
        }
        this.setPage(1);
      });
  }

  setPage(page: number) {
    if (page < 1 || page > this.pager.totalPages) {
      return;
    }
    // get pager object from service
    this.pager = this.pagerService.getPager(this.allItems.length, page, 19);
    // get current page of items
    this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex);
  }

  getPlayerMugshotUrl(mlbPlayerId : string) {
    return this.quoteService.getPlayerMugshotUrl(mlbPlayerId);
  }
}
