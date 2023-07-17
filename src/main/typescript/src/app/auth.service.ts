import { Injectable } from '@angular/core';
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl = environment.apiUrl;

  constructor() { }

  googleLogin() {
    window.open(this.apiUrl + "/oauth/google", "_blank");
  }
}
