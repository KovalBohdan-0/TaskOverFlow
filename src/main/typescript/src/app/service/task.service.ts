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

  constructor(private httpClient: HttpClient, private authService: AuthService, private webSocketService: WebSocketService) { }
  addTask(taskList: any): void {
    this.webSocketService.sendMessage('/app/task-add', JSON.stringify(taskList));
  }
}
