import {Component, Input} from '@angular/core';
import {Notification} from './Notification';
import {TaskUpdateComponent} from "../../task-list/task/task-update/task-update.component";
import {MatDialog} from "@angular/material/dialog";
import {NotificationService} from "../../../service/notification.service";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent {
  @Input()
  notification: Notification;

  constructor(private dialog: MatDialog, private notificationService: NotificationService) {
  }

  readNotification() {
    if (!this.notification.isRead) {
      this.notificationService.readNotification(this.notification.id).subscribe();
      this.notification.isRead = true;
    }
  }

  openTaskModal() {
      this.dialog.open(TaskUpdateComponent, {
        data: this.notification.taskId,
      });
    }
}
