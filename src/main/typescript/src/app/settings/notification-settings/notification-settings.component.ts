import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../../service/customer.service";

@Component({
  selector: 'app-notification-settings',
  templateUrl: './notification-settings.component.html',
  styleUrls: ['./notification-settings.component.css']
})
export class NotificationSettingsComponent implements OnInit {
  customerInfo = {
    email: "",
    emailConfirmed: false,
    onEmailNotifications: false,
    onSiteNotifications: false
  }

  constructor(private customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.customerService.getCurrentCustomer().subscribe(data => {
      this.customerInfo = data.body as any;
    });
  }

  sendEmailConfirmation() {
    this.customerService.sendEmailConfirmation().subscribe();
  }

  updateNotificationSettings() {
    this.customerService.updateNotificationSettings({
      onEmailNotifications: this.customerInfo.onEmailNotifications,
      onSiteNotifications: this.customerInfo.onSiteNotifications
    }).subscribe();
  }
}
