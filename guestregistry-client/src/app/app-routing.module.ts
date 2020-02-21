import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LocationListComponent } from './location/location-list/location-list.component';
import { LocationEditComponent } from './location/location-edit/location-edit.component';
import { EventListComponent } from './event/event-list/event-list.component';
import { EventEditComponent } from './event/event-edit/event-edit.component';


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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
