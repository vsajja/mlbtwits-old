import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuoteService} from "../services/quote.service";
import {AuthenticationService} from "../core/authentication/authentication.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  username : any;
  user: any;
  userTweets : any;

  constructor(private route: ActivatedRoute,
              private quoteService: QuoteService,
              private authenticationService: AuthenticationService) {
    this.username = this.route.snapshot.paramMap.get('username');

    this.quoteService.getUser(this.username).subscribe(
      (res: Response) => {
        this.user = res.json();
      }
    );

    this.quoteService.getUserTweets(this.username).subscribe(
      (res: Response) => {
         this.userTweets = res.json();
      })
  }

  ngOnInit() {
  }

  FIXME_timeAgo(value:string) {
    return this.quoteService.FIXME_timeAgo(value);
  }
}
