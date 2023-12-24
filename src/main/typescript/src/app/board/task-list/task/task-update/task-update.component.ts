import {Component, Inject, OnInit} from '@angular/core';
import {Priority} from "../Priority";
import {TaskService} from "../../../../service/task.service";
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {Task} from "../Task";
import {SharedService} from "../../../../service/shared.service";
import {NotificationService} from "../../../../service/notification.service";
import {Notification} from "./Notification";

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.css']
})
export class TaskUpdateComponent implements OnInit {
  protected readonly Priority = Priority;
  private boardId: number = 0;
  public notification: Notification = {
    id: 0,
    taskId: 0,
    message: '',
    notificationTime: ''
  }
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

  constructor(private taskService: TaskService, private dialog: MatDialog,
  @Inject(MAT_DIALOG_DATA) public data, private sharedService: SharedService, private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.boardId = this.sharedService.boardId;
    this.getTask();
  }

  getTask() {
    return this.taskService.getTask(this.data).subscribe({
      next: (response: any) => {
        this.task = response.body;
        this.task.deadline = this.addUtcHours(this.task.deadline);
        this.getNotification();
      }
    });
  }

  makeNotification() {
    this.notification.id = 1;
    this.notification.notificationTime = this.task.deadline;
  }

  getTaskId(): number {
    return  this.data;
  }

  getNotification() {
    this.notificationService.getNotification(this.task.id).subscribe({
      next: (response: any) => {
        if (response.body.id != null) {
          this.notification = response.body;
          this.notification.notificationTime = this.addUtcHours(this.notification.notificationTime);
        }
      }
    });
  }

  updateTask() {
    this.task.deadline = this.removeUtcHours(this.task.deadline);
    this.taskService.updateTask(this.task, this.boardId);
    if (this.notification.id != 0) {
      this.notification.notificationTime = this.removeUtcHours(this.notification.notificationTime);
      this.notificationService.updateNotification(this.notification, this.task.id).subscribe();
    }
    this.close();
  }

  deleteTask() {
    this.taskService.deleteTask(this.task.id, this.boardId);
    this.close();
  }

  addUtcHours(dateString: string): string {
    const date = new Date(dateString);
    const utcHours = new Date().getTimezoneOffset() / 60 * -1;
    date.setUTCHours(date.getUTCHours() + utcHours);
    return this.dateToIso(date);
  }

  removeUtcHours(dateString: string): string {
    const date = new Date(dateString);
    const utcHours = new Date().getTimezoneOffset() / 60 * -1;
    date.setUTCHours(date.getUTCHours() - utcHours);
    return this.dateToIso(date);
  }

  dateToIso(date: Date): string {
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${
      String(date.getDate()).padStart(2, '0')}T${String(date.getHours()).padStart(2, '0')}:${
      String(date.getMinutes()).padStart(2, '0')}:${String(date.getSeconds()).padStart(2, '0')}`;
  }

  close() {
    this.dialog.closeAll();
  }
}
