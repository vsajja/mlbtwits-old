import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {Route} from '../core/route.service';
import {SettingsComponent} from "./settings.component";

const routes: Routes = Route.withShell([
  {path: 'settings', component: SettingsComponent, data: {title: 'UserSettings'}}
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SettingsRoutingModule {
}
