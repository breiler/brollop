import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule }   from '@angular/router';
import { Routes } from "@angular/router";
import { AlertModule } from 'ng2-bootstrap';
import {HashLocationStrategy, Location, LocationStrategy} from '@angular/common';


import { AppComponent } from './app.component';
import { AddGuestComponent } from "./add-guest.component";
import { WelcomeComponent } from "./welcome.component";
import { ThanksComponent } from "./thanks.component";

import { GuestService } from "./guest.service";

export const routes: Routes = [
    { path: '', component: WelcomeComponent },
    { path: 'guests', component: AddGuestComponent },
    { path: 'add', component: AddGuestComponent },
    { path: 'thanks', component: ThanksComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    AddGuestComponent,
    ThanksComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    ReactiveFormsModule,
    HttpModule,
    AlertModule.forRoot()
  ],
  providers: [GuestService, Location, {provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
