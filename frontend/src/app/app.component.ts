import {Component, ViewContainerRef} from "@angular/core";
import { User, BackendService, Hero, HeroService } from "./shared";
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
    selector:       'app-root',
    templateUrl:    './app.component.html',
    styleUrls:      ['./app.component.scss'],
    providers:      [BackendService]
})
export class AppComponent {

  private viewContainerRef: ViewContainerRef;

  public constructor(viewContainerRef: ViewContainerRef) {
    // You need this small hack in order to catch application root view container ref
    this.viewContainerRef = viewContainerRef;
  }
}
