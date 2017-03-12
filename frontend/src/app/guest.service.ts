import { Injectable } from '@angular/core';
import { Subject }    from 'rxjs/Subject';
import { Guest }      from './model';
import {Http, Response, Headers, RequestOptionsArgs} from "@angular/http";
import {environment} from "../environments/environment";
import 'rxjs/add/operator/timeout';


@Injectable()
export class GuestService {
    guests: Guest[] = [];
    private backendUrlBase = environment.bsEnv.API;

    constructor(private http: Http) {
    }

    addGuest(guest: Guest) : Promise<void>  {
        let p = this.http.post(this.backendUrlBase + '/guests', guest, this.optionsWithHeaders())
            .timeout(4000)
            .map(() => <void>undefined)
            .toPromise();

            p.catch(err => console.log(err));
        return p;
    }

    private optionsWithHeaders():RequestOptionsArgs {
        const headers = new Headers();
        return {headers: headers};
    }
}
