import { BrowserModule } from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
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
import { LoginComponent } from './login/login/login.component';
import {AppService} from './app.service';
import { WorkerEditComponent } from './worker/worker-edit/worker-edit.component';
import { WorkerListComponent } from './worker/worker-list/worker-list.component';
// import {Stuff1} from  '@angular/cdk/overlay';
// import {Stuff2} from  '@angular/cdk/a11y';
// import {Stuff3} from  '@angular/cdk/portal';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}

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
    LoginComponent,
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
  providers: [AppService,
    // full list of available angular locales: https://github.com/angular/angular/tree/master/packages/common/locales
    {provide: OWL_DATE_TIME_LOCALE, useValue: 'lt'},
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
