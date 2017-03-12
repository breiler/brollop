import { Component, OnInit, Input } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { BackendService } from "./shared";
import { Guest } from "./model";
import { GuestService } from "./guest.service";

@Component({
  selector:     'add-guest',
  templateUrl:  './add-guest.html',
  styleUrls:    ['./app.component.scss'],
  providers:    []
})
export class AddGuestComponent implements OnInit {

  public error: string;
  public myForm: FormGroup; // our model driven form


  constructor( private router: Router, private guestService: GuestService, private formBuilder: FormBuilder ) {
  }

  ngOnInit() {
    this.myForm = this.formBuilder.group({
        name: ['', [Validators.required, Validators.minLength(5)]],
        email: ['', [Validators.required, Validators.pattern('^.+@.+$')]],
        phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9\-+ ]+$'), Validators.minLength(5)]],
        notes: ['']
    });
  }

  save() {
    let guest = new Guest();
    guest.name = this.myForm.controls['name'].value;
    guest.email = this.myForm.controls['email'].value;
    guest.phoneNumber = this.myForm.controls['phoneNumber'].value;
    guest.notes = this.myForm.controls['notes'].value;

    this.guestService.addGuest(guest)
        .catch(err => console.log(err))
        .then(() => {this.router.navigate(['/thanks'])});
  }
}
