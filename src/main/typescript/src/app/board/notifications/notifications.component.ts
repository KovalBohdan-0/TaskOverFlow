import {Component, Input} from '@angular/core';
import {Notification} from './notification/Notification';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent {
  @Input()
  notifications: Notification[] = [];
}
