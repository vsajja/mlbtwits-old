import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class QuoteService {
  constructor(private http: Http) {
  }

  getMlbTwits() {
    return this.http.get('/mlbtwits', {cache: true})
      .map(res => res.json())
      .catch(() => Observable.of('Error, could not load trending players'));
  }

  getPlayerLabels() {
    return this.http.get('/playerLabels', {cache: true})
      .map(res => res.json())
      .catch(() => Observable.of('Error, could not load player labels'));
  }

  getTweets() {
    return this.http.get('/tweets', {cache: true})
      .map(res => res.json())
      .catch(() => Observable.of('Error, could not load tweets.'));
  }

  getPlayer(playerId: string): any {
    return this.http.get('/players/' + playerId, {cache: true});
  }

  getPlayers(): any {
    return this.http.get('/players', {cache: true});
  }

  getTeams(): any {
    return this.http.get('/teams', {cache: true});
  }

  getTeam(teamId: string): any {
    return this.http.get('/teams/' + teamId, {cache: true});
  }

  getUsers(): any {
    return this.http.get('/users', {cache: true});
  }

  getUser(username: string): any {
    return this.http.get('/users/' + username, {cache: true});
  }

  getUserTweets(username: string): any {
    return this.http.get('/users/' + username + '/tweets', {cache: true});
  }

  getPlayerTweets(playerId: string): any {
    return this.http.get('/players/' + playerId + '/tweets', {cache: true});
  }
}
