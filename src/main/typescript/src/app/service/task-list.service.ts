import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";
import {RxStompService} from "./rx-stomp.service";

@Injectable({
  providedIn: 'root'
})
export class TaskListService {
  apiUrl = environment.apiUrl;
  headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken(),
  });

  constructor(private httpClient: HttpClient, private authService: AuthService, private rxStompService: RxStompService) { }

  getTaskListsByBoardId(boardId: number) {
    return this.httpClient.get(this.apiUrl + '/api/v1/task-lists/board/' + boardId, {headers: this.headers, observe: 'response'});
  }

  addTaskList(taskList: any, boardId: number) {
    this.rxStompService.publish({destination: "/app/task-list-add/" + boardId, body: JSON.stringify(taskList)});
  }
}
