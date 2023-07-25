import {Component, Inject, OnInit} from '@angular/core';
import {Priority} from "../Priority";
import {TaskService} from "../../../../service/task.service";
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {Task} from "../Task";
import {SharedService} from "../../../../service/shared.service";

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.css']
})
export class TaskUpdateComponent implements OnInit {
  protected readonly Priority = Priority;
  private boardId: number = 0;
  public task: Task = {
    id: 0,
    title: '',
    description: '',
    priority: Priority.LOW,
    taskListId: 0,
    createdAt: '',
    deadline: '',
    done: false
  }

  constructor(private taskService: TaskService,private dialog: MatDialog,
  @Inject(MAT_DIALOG_DATA) public data, private sharedService: SharedService) {
  }

  ngOnInit(): void {
    this.boardId = this.sharedService.boardId;
    this.getTask();
  }

  getTask() {
    return this.taskService.getTask(this.data).subscribe({
      next: (response: any) => {
        this.task = response.body;
      }
    });
  }

  updateTask() {
    this.taskService.updateTask(this.task, this.boardId);
    this.close();
  }

  deleteTask() {
    this.taskService.deleteTask(this.task.id, this.boardId);
    this.close();
  }

  close() {
    this.dialog.closeAll();
  }
}
