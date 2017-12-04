import {Component, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";
import {PagerService} from "../services/pager.service";

declare var require: any
var _ = require('underscore');

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss']
})
export class PlayersComponent implements OnInit {
  players: any;
  allItems: any[];
  pager: any = {};
  pagedItems: any[];
  page = 1;

  constructor(private quoteService: QuoteService, private pagerService: PagerService) {
  }

  ngOnInit() {
    this.refreshPlayers('');
    // this.refreshPlayers(this.defaultSearchTerm);
  }

  searchPlayers(term: string) {
    this.refreshPlayers(term);
  }

  refreshPlayers(term: string) {
    this.quoteService.getPlayers()
      .debounceTime(500)
      .subscribe((data: any) => {
        // FIXME - get all players with a mlbPlayerId
        this.players = data.filter((player: any) => player.mlbPlayerId != null);
        this.allItems = this.players;
        this.allItems = this.allItems.filter(
          (player: any) => player.playerNamePlain.toLowerCase().indexOf(term.toLowerCase()) != -1
        );
        this.setPage(1);
      });
  }

  setPage(page: number) {
    if (page < 1 || page > this.pager.totalPages) {
      return;
    }

    // show all items in search result
    let pageSize = page == 1 ? 18 : this.allItems.length;

    // get pager object from service
    this.pager = this.pagerService.getPager(this.allItems.length, page, pageSize);
    // get current page of items
    // FIXME - why + 1?
    this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex + 1);
  }
}
