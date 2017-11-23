import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {Route} from '../core/route.service';
import {UsersComponent} from "./users.component";

const routes: Routes = Route.withShell([
  {path: 'users', component: UsersComponent, data: {title: 'Users'}}
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class UsersRoutingModule {
}
