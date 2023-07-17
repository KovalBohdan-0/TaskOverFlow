import { Injectable } from '@angular/core';
import {environment} from "../environments/environment";
import {Observable} from "rxjs";
import {UserInfo} from "./login/UserInfo";
import {HttpClient, HttpResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl = environment.apiUrl;


  constructor(private httpClient: HttpClient) { }

  googleLogin() {
    window.open(this.apiUrl + "/oauth/google", "_blank");
  }

  logIn(user: UserInfo): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.apiUrl + '/api/v1/login', user, { observe: 'response' });
  }

  storeToken(token: string) {
    localStorage.setItem("jwt", token);
  }
}
