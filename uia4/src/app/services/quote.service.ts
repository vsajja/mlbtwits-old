import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

const routes = {
  quote: (c: RandomQuoteContext) => `/jokes/random?category=${c.category}`
};

export interface RandomQuoteContext {
  // The quote's category: 'nerdy', 'explicit'...
  category: string;
}

@Injectable()
export class QuoteService {

  constructor(private http: Http) { }

  getRandomQuote(context: RandomQuoteContext): Observable<string> {
    return this.http.get(routes.quote(context), { cache: true })
      .map((res: Response) => res.json())
      .map(body => body.value)
      .catch(() => Observable.of('Error, could not load joke :-('));
  }

  getMlbTwits() {
    return this.http.get('/mlbtwits', { cache: true })
      .map((res: Response) => res.json())
      .catch(() => Observable.of('Error, could not load trending players'));
  }

  getTweets() {
    return this.http.get('/tweets', { cache: true })
      .map((res: Response) => res.json())
      .catch(() => Observable.of('Error, could not load tweets.'));
  }

  getPlayerLabels() {
    return this.http.get('/playerLabels', { cache: true })
      .map((res: Response) => res.json())
      .catch(() => Observable.of('Error, could not load player labels'));
  }
}
