import {Injectable} from "@angular/core";
import {Http, Response, Headers, RequestOptionsArgs} from "@angular/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {User} from "./user";

@Injectable()
export class BackendService {
private backendUrlBase = environment.bsEnv.API;

constructor(private http: Http) {
  }

  getGuests(): Promise<User[]> {
    const p = this.http.get(this.backendUrlBase + '/hello-world?name=test', this.optionsWithHeaders())
      .map(r => r.json())
      .toPromise();
    p.catch(err => this.handleError(err));
    return p;
  }

  private handleError<T>(error: Response|any): Observable<T> {
    console.error('Error: ', error);
    //this.errors.notify('Fel vid anrop till admin backend', error);
    return Observable.throw(undefined);
  }

  private optionsWithHeaders():RequestOptionsArgs {
    const headers = new Headers();
    //headers.append('x-cag-token', localStorage.getItem("cag-admin-token"));
    return {headers: headers};
  }
}
