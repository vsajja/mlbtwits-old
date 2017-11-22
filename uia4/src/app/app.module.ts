import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {TranslateModule} from '@ngx-translate/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';

import {CoreModule} from './core/core.module';
import {SharedModule} from './shared/shared.module';
import {HomeModule} from './home/home.module';
import {LoginModule} from './login/login.module';
import {PlayerComponent} from './player/player.component';
import {PlayersComponent} from './players/players.component';
import {TeamsComponent} from './teams/teams.component';
import {TeamComponent} from './team/team.component';
import {UserComponent} from './user/user.component';
import {UsersComponent} from './users/users.component';
import {PlayerModule} from "./player/player.module";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    TranslateModule.forRoot(),
    NgbModule.forRoot(),
    CoreModule,
    SharedModule,
    HomeModule,
    LoginModule,
    PlayerModule,
    AppRoutingModule
  ],
  declarations: [AppComponent, PlayerComponent, PlayersComponent, TeamsComponent, TeamComponent, UserComponent, UsersComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
