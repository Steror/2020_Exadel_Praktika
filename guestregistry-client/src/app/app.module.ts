import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { LocationListComponent } from './location/location-list/location-list.component';
import { LocationEditComponent } from './location/location-edit/location-edit.component';
import { FormsModule } from '@angular/forms';
import { EventListComponent } from './event/event-list/event-list.component';
import { EventEditComponent } from './event/event-edit/event-edit.component';
import { CardAddComponent } from './card/card-add/card-add.component';
import { CardListDeleteComponent } from './card/card-list-delete/card-list-delete.component';
import { CardUpdateComponent } from './card/card-update/card-update.component';
import {OWL_DATE_TIME_LOCALE, OwlDateTimeModule, OwlNativeDateTimeModule} from 'ng-pick-datetime';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { PersonEditComponent } from './person/person-edit/person-edit.component';
import { PersonListComponent } from './person/person-list/person-list.component';
import { WorkerEditComponent } from './worker/worker-edit/worker-edit.component';
import { WorkerListComponent } from './worker/worker-list/worker-list.component';
// import {Stuff1} from  '@angular/cdk/overlay';
// import {Stuff2} from  '@angular/cdk/a11y';
// import {Stuff3} from  '@angular/cdk/portal';



@NgModule({
  declarations: [
    AppComponent,
    LocationListComponent,
    LocationEditComponent,
    EventListComponent,
    EventEditComponent,
    CardAddComponent,
    CardListDeleteComponent,
    CardUpdateComponent,
    PersonEditComponent,
    PersonListComponent,
    WorkerEditComponent,
    WorkerListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    BrowserAnimationsModule
  ],
  providers: [
    //full list of available angular locales: https://github.com/angular/angular/tree/master/packages/common/locales
    {provide: OWL_DATE_TIME_LOCALE, useValue: 'lt'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
