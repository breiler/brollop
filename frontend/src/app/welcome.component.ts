import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute, Params } from '@angular/router';
import { BackendService } from "./shared";
import { Guest } from "./model";


@Component({
  selector: 'welcome',
  templateUrl: './welcome.html',
  styleUrls: ['./app.component.scss'],
  providers: []
})
export class WelcomeComponent {

  constructor(
    private router: Router,
    private backend: BackendService) {
  }
}
