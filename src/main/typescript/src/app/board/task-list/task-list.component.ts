import {Component, Input, OnInit} from '@angular/core';
import {TaskList} from "./TaskList";
import {TaskFull} from "./task/TaskFull";
import {Priority} from "./task/Priority";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  @Input() taskList: TaskList;
  borderColor: string = "rgb(0, 0, 0)";
  newTask: TaskFull = {
    id: 0,
    title: "",
    done: false,
    priority: Priority.LOW,
    description: "",
    deadlineDate: new Date(),
    taskListId: 0
  };

  constructor() {
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
    const task: TaskFull = {
      id: 0,
      title: "New Task",
      done: false,
      priority: Priority.LOW,
      description: "",
      taskListId: this.taskList.id,
      deadlineDate: new Date()
    }
  }

  protected readonly Priority = Priority;

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.taskList.task, event.previousIndex, event.currentIndex);
  }
}
