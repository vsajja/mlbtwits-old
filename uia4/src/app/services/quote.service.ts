import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {environment} from "../../environments/environment";

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

  getTeamRoster(teamId: string): any {
    return this.http.get('/teams/' + teamId + '/roster', {cache: true});
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

  getPlayerStats(playerId: string): any {
    return this.http.get('/players/' + playerId + '/stats', {cache: true});
  }

  getPlayerMugshotUrl(mlbPlayerId: string): string {
    return environment.serverUrl +  '/players/' + mlbPlayerId + '/mugshot';
  }

  // FIXME
  FIXME_timeAgo(value: string) {
    let d = new Date(value);
    let now = new Date();
    let seconds = Math.round(Math.abs((now.getTime() - d.getTime()) / 1000));
    let minutes = Math.round(Math.abs(seconds / 60));
    let hours = Math.round(Math.abs(minutes / 60));
    let days = Math.round(Math.abs(hours / 24));
    let months = Math.round(Math.abs(days / 30.416));
    let years = Math.round(Math.abs(days / 365));
    if (seconds <= 45) {
      return 'a few seconds ago';
    } else if (seconds <= 90) {
      return 'a minute ago';
    } else if (minutes <= 45) {
      return minutes + ' minutes ago';
    } else if (minutes <= 90) {
      return 'an hour ago';
    } else if (hours <= 22) {
      return hours + ' hours ago';
    } else if (hours <= 36) {
      return 'a day ago';
    } else if (days <= 25) {
      return days + ' days ago';
    } else if (days <= 45) {
      return 'a month ago';
    } else if (days <= 345) {
      return months + ' months ago';
    } else if (days <= 545) {
      return 'a year ago';
    } else { // (days > 545)
      return years + ' years ago';
    }
  }
}
