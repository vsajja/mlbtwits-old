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
  userId: any;
  user: any;
  username: any;
  userTweets : any;

  constructor(private route: ActivatedRoute,
              private quoteService: QuoteService,
              private authenticationService: AuthenticationService) {
    this.userId = this.route.snapshot.paramMap.get('userId');
    this.username = authenticationService.credentials.username;

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
}
