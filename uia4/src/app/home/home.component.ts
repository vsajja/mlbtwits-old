import 'rxjs/add/operator/finally';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/map';

import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {QuoteService} from '../services/quote.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  model: any;
  playerLabels: any;
  tweets: any;
  isLoading: boolean;

  constructor(private quoteService: QuoteService) {
  }


  ngOnInit() {
    this.getPlayerLabels()
    this.getTweets()
  }

  getPlayerLabels() {
    this.isLoading = true;
    this.quoteService.getPlayerLabels()
      .finally(() => {
        this.isLoading = false;
      })
      .subscribe((labels: any) => {
        this.playerLabels = labels;
      });
  }

  getTweets() {
    this.isLoading = true;
    this.quoteService.getTweets()
      .finally(() => {
        this.isLoading = false;
      })
      .subscribe((tweets: any) => {
        this.tweets = tweets;
      });
  }

  tweetPlayer() {
    console.log('TODO: tweet player!');
  }

  FIXME_timeAgo(value:string) {
    return this.quoteService.FIXME_timeAgo(value);
  }

  search = (text$: Observable<string>) =>
    text$
      .debounceTime(100)
      .map(term => term === '' ? []
        : this.playerLabels.filter((player: any) => player.playerName.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));

  formatter = (player: any) => player.playerName;
}
