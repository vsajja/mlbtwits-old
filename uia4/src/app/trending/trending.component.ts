import {Component, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-trending',
  templateUrl: './trending.component.html',
  styleUrls: ['./trending.component.scss']
})
export class TrendingComponent implements OnInit {
  mlbTwits: any;

  constructor(private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.getTrending();
  }

  getTrending() {
    this.quoteService.getMlbTwits().subscribe((data: any) => {
        this.mlbTwits = data;
      });
  }
}
