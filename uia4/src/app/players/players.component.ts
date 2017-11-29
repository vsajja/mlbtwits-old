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

  // array of all items to be paged
  private allItems: any[];
  // pager object
  pager: any = {};
  // paged items
  pagedItems: any[];

  constructor(private quoteService: QuoteService, private pagerService: PagerService) {
  }

  ngOnInit() {
    this.refreshPlayers();
  }

  searchPlayers(term: string) {
    if(term) {
      this.refreshPlayers();
      this.allItems = this.players.filter(
        (player: any) => player.playerNamePlain.toLowerCase().indexOf(term.toLowerCase()) > -1
      )
    }
  }

  refreshPlayers() {
    this.quoteService.getPlayers()
      .subscribe((data: any) => {
        this.players = data.filter((player: any) => player.mlbPlayerId != null);
        this.allItems = this.players;
        this.setPage(1);
      });
  }

  setPage(page: number) {
    if (page < 1 || page > this.pager.totalPages) {
      return;
    }

    // FIXME - timing issue - page is set to NaN and sometimes the players haven't been loaded yet
    // these guards are required to avoid console errors
    if(!this.allItems) {
      return;
    }
    if(!page) {
      page = 1;
    }
    // console.log(page);
    // console.log(this.allItems.length);

    // get pager object from service
    this.pager = this.pagerService.getPager(this.allItems.length, page, 19);
    // get current page of items
    this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex);
  }

  getPlayerMugshotUrl(mlbPlayerId : string) {
    return this.quoteService.getPlayerMugshotUrl(mlbPlayerId);
  }
}
