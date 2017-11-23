import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {Route} from '../core/route.service';
import {TeamsComponent} from "./teams.component";

const routes: Routes = Route.withShell([
  {path: 'teams', component: TeamsComponent, data: {title: 'Teams'}}
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class TeamsRoutingModule {
}
