import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {Route} from '../core/route.service';
import {PlayersComponent} from "./players.component";

const routes: Routes = Route.withShell([
  {path: 'players', component: PlayersComponent, data: {title: 'Players'}}
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class PlayersRoutingModule {
}
