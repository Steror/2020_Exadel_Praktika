import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
//sito reikia, kad krautu
import {HttpClientModule} from "@angular/common/http";
import {CardServiceService} from "./card/card-service.service";
import { CardUpdateComponent } from './card/card-update/card-update.component';
import { CardListDeleteComponent } from './card/card-list-delete/card-list-delete.component';
import { CardAddComponent } from './card/card-add/card-add.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    CardUpdateComponent,
    CardListDeleteComponent,
    CardAddComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  // providers: [CardServiceService],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
