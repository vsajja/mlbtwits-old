import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {CoreModule} from './core/core.module';
import {HomeModule} from './home/home.module';
import {LoginModule} from './login/login.module';
import {PlayerComponent} from './player/player.component';
import {PlayersComponent} from './players/players.component';
import {TeamsComponent} from './teams/teams.component';
import {TeamComponent} from './team/team.component';
import {UserComponent} from './user/user.component';
import {UsersComponent} from './users/users.component';
import {PlayerModule} from "./player/player.module";
import {PlayersModule} from "./players/players.module";
import {TeamsModule} from "./teams/teams.module";
import {TeamModule} from "./team/team.module";
import {UsersModule} from "./users/users.module";
import {UserModule} from "./user/user.module";
import {SettingsComponent} from './settings/settings.component';
import {SettingsModule} from "./settings/settings.module";
import {NgxDatatableModule} from "@swimlane/ngx-datatable";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {APIInterceptor} from "./core/http/http-interceptor";
import {PagerService} from "./services/pager.service";
import {PlayerCardComponent} from './player-card/player-card.component';
import { TeamCardComponent } from './team-card/team-card.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    TranslateModule.forRoot(),
    NgbModule.forRoot(),
    CoreModule,
    HomeModule,
    LoginModule,
    PlayerModule,
    PlayersModule,
    TeamModule,
    TeamsModule,
    UserModule,
    UsersModule,
    SettingsModule,
    NgxDatatableModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    PlayerComponent,
    PlayersComponent,
    TeamsComponent,
    TeamComponent,
    UserComponent,
    UsersComponent,
    SettingsComponent,
    PlayerCardComponent,
    TeamCardComponent
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: APIInterceptor,
    multi: true,
  },
    PagerService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
