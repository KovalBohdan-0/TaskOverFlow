import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {environment} from "../../environments/environment";
import {RxStompService} from "./rx-stomp.service";

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  apiUrl = environment.apiUrl;
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken(),
  });

  constructor(private httpClient: HttpClient, private authService: AuthService, private rxStompService: RxStompService) { }
  addTask(taskList: any, boardId): void {
    this.rxStompService.publish({destination: '/app/task-add/' + boardId , body: JSON.stringify(taskList)});
  }
}
