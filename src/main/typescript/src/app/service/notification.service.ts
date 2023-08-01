import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {UpdateNotification} from "../board/task-list/task/task-update/UpdateNotification";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  apiURL: string = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  getNotification(taskId: number) {
    return this.httpClient.get(this.apiURL + '/api/v1/notification/' + taskId, {observe: 'response'});
  }

  updateNotification(notification: UpdateNotification, taskId: number) {
    return this.httpClient.put(this.apiURL + '/api/v1/notification/' + taskId, notification, {observe: 'response'});
  }
}
