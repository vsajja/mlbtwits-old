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

    this.quoteService.getTeam(this.teamId).subscribe(
      (res: Response) => {
        this.team = res.json();
      }
    );

    this.quoteService.getTeamRoster(this.teamId).subscribe(
      (res: Response) => {
        this.roster = res.json();
      }
    );
  }
}
