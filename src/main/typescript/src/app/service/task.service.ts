import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {environment} from "../../environments/environment";
import {WebSocketService} from "./web-socket.service";

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  apiUrl = environment.apiUrl;
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken(),
  });
  client = this.webSocketService.getClient();

  constructor(private httpClient: HttpClient, private authService: AuthService, private webSocketService: WebSocketService) { }

  // addTask(task: any) {
  //   return this.httpClient.post(this.apiUrl + '/api/v1/task', task, {headers: this.headers});
  // }

  sendTaskAddedMessage(taskList: any): void {
    this.client.publish({
      destination: '/app/task-added',
      body: JSON.stringify(taskList)
    });
  }
}
