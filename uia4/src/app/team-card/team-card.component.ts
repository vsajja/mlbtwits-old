import {Component, Input, OnInit} from '@angular/core';
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-team-card',
  templateUrl: './team-card.component.html',
  styleUrls: ['./team-card.component.scss']
})
export class TeamCardComponent implements OnInit {

  @Input() team: any;

  constructor(private quoteService: QuoteService) {
  }

  ngOnInit() {
  }

  getTeamLogoUrl() {
    return "http://mlb.com/mlb/images/team_logos/logo_" + this.team.teamCodeMlb.toLowerCase() + "_79x76.jpg"
  }
}
