import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {Route} from '../core/route.service';
import {TeamComponent} from "./team.component";

const routes: Routes = Route.withShell([
  {path: 'teams/:teamId', component: TeamComponent, data: {title: 'Team'}}
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class TeamRoutingModule {
}
