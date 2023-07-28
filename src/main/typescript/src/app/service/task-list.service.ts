import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";
import {RxStompService} from "./rx-stomp.service";
import {TaskList} from "../board/task-list/TaskList";
import {TaskListUpdateSort} from "../board/task-list/TaskListUpdateSort";
import {SortOption} from "../board/task-list/task/SortOption";
import {SortDirection} from "../board/task-list/task/SortDirection";

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

  deleteTaskList(taskList: TaskList): void {
    this.rxStompService.publish({destination: '/app/task-list-delete/' + taskList.boardId , body: JSON.stringify(taskList.id)});
  }

  renameTaskList(taskList: TaskList): void {
    this.rxStompService.publish({destination: '/app/task-list-rename/' + taskList.boardId , body: JSON.stringify({title: taskList.title, taskListId: taskList.id})});
  }

  moveTaskList(taskListBeforeId, taskListAfterId, taskListId, boardId): void {
    this.rxStompService.publish({destination: '/app/task-list-move/' + boardId , body: JSON.stringify({
        taskListBeforeId: taskListBeforeId,
        taskListAfterId: taskListAfterId,
        taskListId: taskListId})});
  }

  updateTaskListSort(updateSort: TaskListUpdateSort, boardId): void {
    this.rxStompService.publish({destination: '/app/task-list-update-sort/' + boardId , body: JSON.stringify(updateSort)});
  }

  sortTaskList(taskList: TaskList): TaskList {
    const multiplier = taskList.sortDirection === SortDirection.ASC ? 1 : -1;

    if (taskList.sortOption === SortOption.POSITION) {
      taskList.tasks.sort((a, b) => (a.position - b.position) * multiplier);
    } else if (taskList.sortOption === SortOption.CREATED_AT) {
      taskList.tasks.sort((a, b) => (Date.parse(a.createdAt) - Date.parse(b.createdAt) * multiplier));
    } else if (taskList.sortOption === SortOption.DEADLINE) {
      taskList.tasks.sort((a, b) => (Date.parse(a.deadline) - Date.parse(b.deadline) * multiplier));
    } else if (taskList.sortOption === SortOption.PRIORITY) {
      taskList.tasks.sort((a, b) => {
        const aPriority = a.priority == "LOW" ? 0 : a.priority == "MEDIUM" ? 1 : 2;
        const bPriority = b.priority == "LOW" ? 0 : b.priority == "MEDIUM" ? 1 : 2;
        return (aPriority - bPriority) * multiplier;
      });
    } else if (taskList.sortOption === SortOption.TITLE) {
      taskList.tasks.sort((a, b) => (a.title.localeCompare(b.title) * multiplier));
    }

    return taskList;
  }
}
