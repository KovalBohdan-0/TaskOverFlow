import {Component, Input, OnInit} from '@angular/core';
import {TaskList} from "./TaskList";
import {TaskCreation} from "./task/TaskFull";
import {Priority} from "./task/Priority";
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";
import {Task} from "./task/Task";
import {TaskService} from "../../service/task.service";
import {WebSocketService} from "../../service/web-socket.service";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  @Input() taskList: TaskList;
  borderColor: string = "rgb(0, 0, 0)";
  newTask: TaskCreation = {
    title: "",
    priority: Priority.LOW,
    taskListId: 0
  };

  constructor(private taskService: TaskService,
              private webSocketService: WebSocketService) {

  }

  ngOnInit(): void {
    this.borderColor = this.stringToColor(this.taskList.title);
    this.makeSubscriptions()
  }

  stringToColor(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
      hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }

    hash = Math.abs(hash);

    const r = hash % 255;
    const g = (hash >> 8) % 255;
    const b = (hash >> 16) % 255;

    return `rgb(${r}, ${g}, ${b})`;
  }

  makeSubscriptions() {
    this.webSocketService.getTaskAdditions().subscribe(message => {
      if (message.taskListId == this.taskList.id) {
        this.taskList.tasks.push(message);
      }
    });
  }

  addTask() {
    this.newTask.taskListId = this.taskList.id;
    this.taskService.addTask(this.newTask);
  }

  protected readonly Priority = Priority;

  drop(event: CdkDragDrop<Task[], any>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
    }
  }
}
