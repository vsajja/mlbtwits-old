import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {CoreModule} from '../core/core.module';
import {HomeRoutingModule} from './home-routing.module';
import {HomeComponent} from './home.component';
import {QuoteService} from '../services/quote.service';
import {TrendingComponent} from "../trending/trending.component";
import {NgbTypeaheadModule} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    CoreModule,
    HomeRoutingModule,
    NgbTypeaheadModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    HomeComponent, TrendingComponent
  ],
  providers: [
    QuoteService
  ]
})
export class HomeModule {
}
