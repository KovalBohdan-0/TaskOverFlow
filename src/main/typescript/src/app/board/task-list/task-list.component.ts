import {Component, Input, OnInit} from '@angular/core';
import {TaskList} from "./TaskList";
import {TaskCreation} from "./task/TaskFull";
import {Priority} from "./task/Priority";
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";
import {ShortTask} from "./task/ShortTask";
import {TaskService} from "../../service/task.service";
import {TaskListService} from "../../service/task-list.service";
import {TaskListUpdateSort} from "./TaskListUpdateSort";
import {SortOption} from "./task/SortOption";

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
    this.taskListService.sortTaskList(this.taskList);
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

  updateTaskListSort(sortOption: string) {
    let sortDirection: string = "ASC";

    if (this.taskList.sortOption === sortOption) {
      if (this.taskList.sortDirection === "ASC") {
        sortDirection = "DESC";
      } else {
        sortDirection = "ASC";
      }
    }

    const taskListUpdateSort: TaskListUpdateSort = {
      id: this.taskList.id,
      sortOption: sortOption,
      sortDirection: sortDirection
    }

    this.taskListService.updateTaskListSort(taskListUpdateSort, this.taskList.boardId);
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

    const taskBefore = event.container.data[event.currentIndex - 1];
    const taskAfter = event.container.data[event.currentIndex + 1];

    this.taskService.moveTask({
      taskId: event.container.data[event.currentIndex].id,
      taskListId: this.taskList.id,
      taskBeforeId: taskBefore ? taskBefore.id : -1,
      taskAfterId: taskAfter ? taskAfter.id : -1
    }, this.taskList.boardId);
  }

  protected readonly SortOption = SortOption;
}
