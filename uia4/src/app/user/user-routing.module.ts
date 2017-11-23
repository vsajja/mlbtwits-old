import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {Route} from '../core/route.service';
import {UserComponent} from "./user.component";

const routes: Routes = Route.withShell([
  {path: 'users/:userId', component: UserComponent, data: {title: 'User'}}
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class UserRoutingModule {
}
