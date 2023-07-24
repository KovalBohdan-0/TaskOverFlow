import {Component, Input, OnInit} from '@angular/core';
import {TaskList} from "./TaskList";
import {TaskCreation} from "./task/TaskFull";
import {Priority} from "./task/Priority";
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";
import {ShortTask} from "./task/ShortTask";
import {TaskService} from "../../service/task.service";
import {TaskListService} from "../../service/task-list.service";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  @Input() taskList: TaskList;
  borderColor: string = "rgb(0, 0, 0)";
  newTitle: string = "";
  changeTitle: boolean = false;
  newTask: TaskCreation = {
    title: "",
    priority: Priority.LOW,
    taskListId: 0
  };

  constructor(private taskService: TaskService, private taskListService: TaskListService) {

  }

  ngOnInit(): void {
    this.borderColor = this.stringToColor(this.taskList.title);
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

  addTask() {
    this.newTask.taskListId = this.taskList.id;
    this.taskService.addTask(this.newTask, this.taskList.boardId);
  }

  protected readonly Priority = Priority;

  deleteTaskList() {
    this.taskListService.deleteTaskList(this.taskList);
  }

  replaceWithInput() {
    this.changeTitle = true;
    this.newTitle = this.taskList.title;
  }

  replaceWithText() {
    this.changeTitle = false;
    this.taskList.title = this.newTitle;
    this.taskListService.renameTaskList(this.taskList);
    this.borderColor = this.stringToColor(this.taskList.title);
  }

  drop(event: CdkDragDrop<ShortTask[], any>) {
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
