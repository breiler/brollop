import { Component, OnInit, Input } from "@angular/core";
import { Router, ActivatedRoute, Params } from '@angular/router';

import { BackendService } from "./shared";
import { Guest } from "./model";
import { GuestService } from "./guest.service";

@Component({
  selector: 'add-guest',
  templateUrl: './add-guest.html',
  styleUrls: ['./app.component.scss'],
  providers: []
})
export class AddGuestComponent {

  private guest: Guest;

  constructor( private router: Router, private guestService: GuestService ) {
    this.guest = new Guest();
  }

  save() {
    //this.guestService.guests.push(this.guest);
    this.guestService.addGuest(this.guest);
    this.router.navigate(['/thanks']);
  }
}
