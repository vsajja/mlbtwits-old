import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuoteService} from "../services/quote.service";

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.scss']
})
export class TeamComponent implements OnInit {
  teamId: any;
  team: any;
  roster: any;

  constructor(private route: ActivatedRoute, private quoteService: QuoteService) {
  }

  ngOnInit() {
    this.teamId = this.route.snapshot.paramMap.get('teamId');
    this.getTeam(this.teamId);
    this.getTeamRoster(this.teamId);
  }

  getTeam(teamId: string) {
    this.quoteService.getTeam(teamId).subscribe(
      (data: any) => {
        this.team = data;
      });
  }

  getTeamRoster(teamId: string) {
    this.quoteService.getTeamRoster(teamId).subscribe(
      (data: any) => {
        this.roster = data;
      });
  }
}
