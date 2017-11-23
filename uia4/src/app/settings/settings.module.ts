import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SettingsRoutingModule} from "./settings-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    TranslateModule,
    NgbModule,
    SettingsRoutingModule
  ],
  declarations: []
})
export class SettingsModule {
}
