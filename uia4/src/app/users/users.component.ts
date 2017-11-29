import {Component, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  users: any;

  constructor(private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.getUsers();
  }

  searchUsers(term: string) {
    this.getUsers();
    if (term) {
      this.users = this.users.filter(
        (user: any) => user.username.toLowerCase().indexOf(term.toLowerCase()) > -1
      );
    }
  }

  getUsers() {
    this.quoteService.getUsers()
      .subscribe((data: any) => {
          this.users = data;
        }
      );
  }
}
