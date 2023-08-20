import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {UpdateNotification} from "../board/task-list/task/task-update/UpdateNotification";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  apiURL: string = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  getNotification(taskId: number): Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.apiURL + '/api/v1/notification/' + taskId, {observe: 'response'});
  }

  getCurrentNotifications(): Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.apiURL + '/api/v1/notification', {observe: 'response'});
  }

  updateNotification(notification: UpdateNotification, taskId: number): Observable<HttpResponse<Object>> {
    return this.httpClient.put(this.apiURL + '/api/v1/notification/' + taskId, notification, {observe: 'response'});
  }

  readCurrentNotifications(): Observable<HttpResponse<Object>> {
    return this.httpClient.put(this.apiURL + '/api/v1/notification/read', null, {observe: 'response'});
  }
}
