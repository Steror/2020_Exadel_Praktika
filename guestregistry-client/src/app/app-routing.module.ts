import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LocationListComponent } from './location/location-list/location-list.component';
import { LocationEditComponent } from './location/location-edit/location-edit.component';
import { EventListComponent } from './event/event-list/event-list.component';
import { EventEditComponent } from './event/event-edit/event-edit.component';
import {CardListDeleteComponent} from "./card/card-list-delete/card-list-delete.component";
import {CardAddComponent} from "./card/card-add/card-add.component";
import {CardUpdateComponent} from "./card/card-update/card-update.component";
import {PersonEditComponent} from "./person/person-edit/person-edit.component";
import {PersonListComponent} from "./person/person-list/person-list.component";
import {WorkerEditComponent} from "./worker/worker-edit/worker-edit.component";
import {WorkerListComponent} from "./worker/worker-list/worker-list.component";


const routes: Routes = [
  { path: '', redirectTo: '/location-list', pathMatch: 'full' },
  {
    path: 'location-list',
    component: LocationListComponent
  },
  {
    path: 'location-add',
    component: LocationEditComponent
  },
  {
    path: 'location-edit/:id',
    component: LocationEditComponent
  },
  {
    path: 'event-list',
    component: EventListComponent
  },
  {
    path: 'event-add',
    component: EventEditComponent
  },
  {
    path: 'event-edit/:id',
    component: EventEditComponent
  },

  {path:"card/list", component:CardListDeleteComponent},
  {path:"card/add", component:CardAddComponent},
  {path:"card/update/:id", component:CardUpdateComponent},
  {path: 'person-list', component: PersonListComponent},
  {path: 'person-add', component: PersonEditComponent},
  {path: 'person-edit/:id', component: PersonEditComponent},
  {path: 'worker-list', component: WorkerListComponent},
  {path: 'worker-add', component: WorkerEditComponent},
  {path: 'worker-edit/:id', component: WorkerEditComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
