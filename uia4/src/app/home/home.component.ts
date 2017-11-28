import 'rxjs/add/operator/finally';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/map';

import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {QuoteService} from '../services/quote.service';
import {AuthenticationService} from "../core/authentication/authentication.service";

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
  username: any;
  user: any;

  constructor(private quoteService: QuoteService, private authService : AuthenticationService) {
    this.username = authService.credentials.username;

    this.quoteService.getUser(this.username).subscribe(
      (res: Response) => {
        this.user = res.json();
      }
    );
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

  tweet(player : any) {
    let message = '[~' + player.playerName + '] ' + 'test FIXME_userTweet!'
    this.quoteService.FIXME_userTweet(this.user, message);
    // FIXME: refresh the tweets (better use live feeds)
    this.getTweets();
  }

  FIXME_timeAgo(value:string) {
    return this.quoteService.FIXME_timeAgo(value);
  }

  getPlayerMugshotUrl(mlbPlayerId: string) {
    return this.quoteService.getPlayerMugshotUrl(mlbPlayerId);
  }

  search = (text$: Observable<string>) =>
    text$
      .debounceTime(100)
      .map(term => term === '' ? []
        : this.playerLabels.filter((player: any) => player.playerName.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));

  formatter = (player: any) => player.playerName;
}
