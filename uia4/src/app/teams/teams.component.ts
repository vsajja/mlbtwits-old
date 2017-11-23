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
    this.refreshTeams();
  }

  searchTeams(term: string) {
    // FIXME: try not to load all teams
    this.refreshTeams();
    if (term) {
      this.teams = this.teams.filter(
        (team: any) => team.teamName.toLowerCase().indexOf(term.toLowerCase()) > -1
      )
    }
  }

  refreshTeams() {
    this.quoteService.getTeams()
      .subscribe((res: Response) => {
          this.teams = res.json();
        }
      );
  }
}
