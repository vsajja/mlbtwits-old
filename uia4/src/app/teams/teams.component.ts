import {Component, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss']
})
export class TeamsComponent implements OnInit {
  teams: any;

  constructor(private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.getTeams();
  }

  searchTeams(term: string) {
    this.getTeams();
    if (term) {
      this.teams = this.teams.filter(
        (team: any) => team.teamName.toLowerCase().indexOf(term.toLowerCase()) > -1
      )
    }
  }

  getTeams() {
    this.quoteService.getTeams()
      .subscribe((data: any) => {
        this.teams = data;
      });
  }
}
