import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {Route} from '../core/route.service';
import {PlayerComponent} from "./player.component";

const routes: Routes = Route.withShell([
  {path: 'players/:playerId', component: PlayerComponent, data: {title: 'Player'}}
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class PlayerRoutingModule {
}
