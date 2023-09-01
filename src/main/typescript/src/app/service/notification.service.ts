import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {UpdateNotification} from "../board/task-list/task/task-update/UpdateNotification";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  apiURL: string = environment.apiUrl;
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken(),
  });

  constructor(private httpClient: HttpClient, private authService: AuthService) { }

  getNotification(taskId: number): Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.apiURL + '/api/v1/notification/' + taskId, {observe: 'response', headers: this.headers});
  }

  getCurrentNotifications(): Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.apiURL + '/api/v1/notification', {observe: 'response', headers: this.headers});
  }

  updateNotification(notification: UpdateNotification, taskId: number): Observable<HttpResponse<Object>> {
    return this.httpClient.put(this.apiURL + '/api/v1/notification/' + taskId, notification, {observe: 'response', headers: this.headers});
  }

  readNotification(id: number): Observable<HttpResponse<Object>> {
    return this.httpClient.put(this.apiURL + '/api/v1/notification/read/' + id, null, {observe: 'response', headers: this.headers});
  }
}
