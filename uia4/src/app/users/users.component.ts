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
    this.refreshUsers();
  }

  searchUsers(term: string) {
    this.refreshUsers();
    if (term) {
      this.users = this.users.filter(
        (user: any) => user.username.toLowerCase().indexOf(term.toLowerCase()) > -1
      )
    }
  }

  refreshUsers() {
    this.quoteService.getUsers()
      .subscribe((res: Response) => {
          this.users = res.json();
        }
      );
  }
}
