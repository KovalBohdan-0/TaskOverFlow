import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {UserInfo} from "../login/UserInfo";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl = environment.apiUrl;


  constructor(private httpClient: HttpClient, private router: Router) { }

  googleLogin() {
    window.open(this.apiUrl + "/oauth2/google", "_blank");
    this.getJwtFromCookies().then(jwt => {
      if (jwt) {
        this.storeToken(jwt);
        this.removeCookie("jwt");
        this.router.navigate(['/board']);
      }
    } );
  }

  logIn(user: UserInfo): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.apiUrl + '/api/v1/login', user, { observe: 'response' });
  }

  logOut() {
    localStorage.removeItem("jwt");
    this.router.navigate(['/login']);
  }

  register(user: UserInfo): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.apiUrl + '/api/v1/registration', user, { observe: 'response' });
  }

  storeToken(token) {
    localStorage.setItem("jwt", token);
  }

  getToken() : string {
    return localStorage.getItem("jwt") || "";
  }

  getJwtFromCookies() {
    return new Promise((resolve) => {
      let jwtFound = false;
      for (let i = 0; i < 5; i++) {
        setTimeout(function () {
          if (!jwtFound) {
            for (let cookie of document.cookie.split(';')) {
              if (cookie.includes('jwt')) {
                jwtFound = true;
                resolve(cookie.split('=')[1]);
                return;
              }
            }
          }

          if (!jwtFound && i === 4) {
            resolve("");
          }
        }, 3000 * i);
      }
    });
  }

  removeCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
  }
}
