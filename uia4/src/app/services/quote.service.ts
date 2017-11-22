import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class QuoteService {
  player : any;

  constructor(private http: Http) {
  }

  getMlbTwits() {
    return this.http.get('/mlbtwits', {cache: true})
      .map(res => res.json())
      .catch(() => Observable.of('Error, could not load trending players'));
  }

  getTweets() {
    return this.http.get('/tweets', {cache: true})
      .map(res => res.json())
      .catch(() => Observable.of('Error, could not load tweets.'));
  }

  getPlayer(playerId: string): any {
    return this.http.get('/players/' + playerId, {cache: true})
  }

  getPlayerLabels() {
    return this.http.get('/playerLabels', {cache: true})
      .map(res => res.json())
      .catch(() => Observable.of('Error, could not load player labels'));
  }
}
