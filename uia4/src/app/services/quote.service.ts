import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {environment} from "../../environments/environment";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";

@Injectable()
export class QuoteService {
  constructor(private httpClient: HttpClient) {
  }

  getMlbTwits(): any {
    return this.httpClient.get('/mlbtwits');
  }

  getPlayerLabels() {
    return this.httpClient.get('/playerLabels');
  }

  getTweets() {
    return this.httpClient.get('/tweets');
  }

  getPlayer(playerId: string): any {
    return this.httpClient.get('/players/' + playerId);
  }

  getPlayers(): any {
    return this.httpClient.get('/players');
  }

  getTeams(): any {
    return this.httpClient.get('/teams');
  }

  getTeam(teamId: string): any {
    return this.httpClient.get('/teams/' + teamId);
  }

  getTeamRoster(teamId: string): any {
    return this.httpClient.get('/teams/' + teamId + '/roster');
  }

  getUsers(): any {
    return this.httpClient.get('/users');
  }

  getUser(username: string): any {
    return this.httpClient.get('/users/' + username);
  }

  getUserTweets(username: string): any {
    return this.httpClient.get('/users/' + username + '/tweets');
  }

  getPlayerTweets(playerId: string): any {
    return this.httpClient.get('/players/' + playerId + '/tweets');
  }

  getPlayerStats(playerId: string): any {
    return this.httpClient.get('/players/' + playerId + '/stats');
  }

  getPlayerMugshotUrl(mlbPlayerId: string): string {
    return environment.serverUrl + '/players/' + mlbPlayerId + '/mugshot';
  }

  calculateAge(birthDate: any) {
    var ageDifMs = Date.now() - birthDate;
    var ageDate = new Date(ageDifMs); // miliseconds from epoch
    return Math.abs(ageDate.getUTCFullYear() - 1970);
  }

  FIXME_userTweet(user: any, message: string) {
    let userId = user.userId;
    let username = user.username;
    return this.httpClient.post('/users/' + user.username + '/tweets', {
      userId: userId,
      message: message
    });
  }

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
