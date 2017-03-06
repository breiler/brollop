import { Component, OnInit, Input } from "@angular/core";
import { Router, ActivatedRoute, Params } from '@angular/router';

import { BackendService } from "./shared";
import { Guest } from "./model";
import { GuestService } from "./guest.service";

@Component({
  selector: 'thanks',
  templateUrl: './thanks.html',
  styleUrls: ['./app.component.scss'],
  providers: []
})
export class ThanksComponent implements OnInit {

  private guest: Guest;
  private sub: any;

  constructor( private route: ActivatedRoute, private router: Router, private guestService: GuestService ) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
       let index = +params['index'];
       this.guest = this.guestService.guests[index];
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
