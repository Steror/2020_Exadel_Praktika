import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {CardAddComponent} from "./card/card-add/card-add.component";
import {CardListDeleteComponent} from "./card/card-list-delete/card-list-delete.component";
import {CardUpdateComponent} from "./card/card-update/card-update.component";


const routes: Routes = [
  {path:"card/list", component:CardListDeleteComponent},
  {path:"card/add", component:CardAddComponent},
  {path:"card/update/:id", component:CardUpdateComponent}
//  {path:"", redirectTo:"search", pathMatch:"full"},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
