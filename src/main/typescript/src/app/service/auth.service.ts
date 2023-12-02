import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {fromEvent, Observable} from "rxjs";
import {UserInfo} from "../login/UserInfo";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl: string = environment.apiUrl;
  redirectUrl: string = environment.redirectUrl;


  constructor(private httpClient: HttpClient, private router: Router) { }

  healthCheck(): Observable<Object> {
    return this.httpClient.get(this.apiUrl + "/api/v1/health-check");
  }

  googleLogin() {
    window.open(this.redirectUrl + "/oauth2/google", "_blank");
    this.loginWithOAuthJwt();
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

  isLoggedIn() : boolean {
    return this.getToken() !== "";
  }

  ifLoggedIn() : void {
    fromEvent(window, 'storage').subscribe((storageEvent: Event): void => {
      if (this.isLoggedIn()) {
        this.router.navigate(['/board']);
      }
    })

    if (this.isLoggedIn()) {
      this.router.navigate(['/board']);
    }
  }

  loginWithOAuthJwt(): void {
    fromEvent(window, 'cookie').subscribe((cookieEvent: Event): void => {
      this.findJwtFromCookie();
    })

    this.findJwtFromCookie();
  }

  findJwtFromCookie() : void {
    for (let cookie of document.cookie.split(';')) {
      if (cookie.startsWith("jwt") && cookie.split('=').length > 1) {
        const jwt : string = cookie.split('=')[1];
        this.storeToken(jwt);
        this.removeCookie("jwt");
        this.router.navigate(['/board']);
      }
    }
  }

  removeCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
  }
}
