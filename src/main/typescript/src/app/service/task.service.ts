import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {environment} from "../../environments/environment";
import {RxStompService} from "./rx-stomp.service";
import {Observable} from "rxjs";
import {Task} from "../board/task-list/task/Task";

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  apiUrl = environment.apiUrl;
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken(),
  });

  constructor(private httpClient: HttpClient, private authService: AuthService, private rxStompService: RxStompService) { }

  getTask(taskId: number): Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.apiUrl + '/api/v1/task/' + taskId, {headers: this.headers, observe: "response"});
  }

  addTask(taskList: any, boardId: number): void {
    this.rxStompService.publish({destination: '/app/task-add/' + boardId , body: JSON.stringify(taskList)});
  }

  updateTask(task: Task, boardId: number): void {
    this.rxStompService.publish({destination: '/app/task-update/' + boardId, body: JSON.stringify(task)});
  }

  deleteTask(taskId: number, boardId: number): void {
    this.rxStompService.publish({destination: '/app/task-delete/' + boardId, body: JSON.stringify(taskId)});
  }
}
