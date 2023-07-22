import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {UserInfo} from "../login/UserInfo";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {WebSocketService} from "./web-socket.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl = environment.apiUrl;


  constructor(private httpClient: HttpClient, private webSocketService: WebSocketService, private router: Router) { }

  googleLogin() {
    window.open(this.apiUrl + "/oauth2/google", "_blank");
    this.webSocketService.connect();
    this.webSocketService.getAuth2().subscribe((message) => {
      this.storeToken(message.jwt);
      this.router.navigate(['/board']);
      this.webSocketService.disconnect();
    });
  }

  logIn(user: UserInfo): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.apiUrl + '/api/v1/login', user, { observe: 'response' });
  }

  register(user: UserInfo): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.apiUrl + '/api/v1/registration', user, { observe: 'response' });
  }

  storeToken(token: string) {
    localStorage.setItem("jwt", token);
  }

  getToken() : string {
    return localStorage.getItem("jwt") || "";
  }
}
