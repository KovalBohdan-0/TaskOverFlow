import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";
import {NotificationSettings} from "../settings/notification-settings/NotificationSettings";
import {PasswordUpdate} from "../settings/password-settings/PasswordUpdate";
import {EmailUpdate} from "../settings/account-settings/EmailUpdate";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  apiUrl = environment.apiUrl;
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken(),
  });

  constructor(private httpClient: HttpClient, private authService: AuthService) { }

  getCurrentCustomer(): Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.apiUrl + '/api/v1/customer', {headers: this.headers, observe: 'response'});
  }

  sendEmailConfirmation(): Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.apiUrl + '/api/v1/sendConfirmationEmail', {headers: this.headers, observe: 'response'});
  }

  updateNotificationSettings(notificationSettings: NotificationSettings): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.apiUrl + '/api/v1/customer/update-notifications', notificationSettings, {headers: this.headers, observe: 'response'});
  }

  updatePassword(passwordUpdate: PasswordUpdate): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.apiUrl + '/api/v1/customer/update-password', passwordUpdate, {headers: this.headers, observe: 'response'});
  }

  updateEmail(emailUpdate: EmailUpdate): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.apiUrl + '/api/v1/customer/update-email', emailUpdate, {headers: this.headers, observe: 'response'});
  }
}
