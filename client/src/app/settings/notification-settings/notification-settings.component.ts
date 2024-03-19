import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../../service/customer.service";
import {MatSnackBar} from "@angular/material/snack-bar";

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

  constructor(private customerService: CustomerService, private matSnackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.customerService.getCurrentCustomer().subscribe(data => {
      this.customerInfo = data.body as any;
    });
  }

  sendEmailConfirmation() {
    this.customerService.sendEmailConfirmation().subscribe({
        next: () => {
          this.matSnackBar.open("Confirmation email sent", "Close", {duration: 3000});
        },
        error: (error) => {
          if (error.status === 409) {
            this.matSnackBar.open("Email already sent", "Close", {duration: 5000});
          } else {
            this.matSnackBar.open("Error while sending email", "Close", {duration: 5000});
          }
        }
      }
    );
  }

  updateNotificationSettings() {
    this.customerService.updateNotificationSettings({
      onEmailNotifications: this.customerInfo.onEmailNotifications,
      onSiteNotifications: this.customerInfo.onSiteNotifications
    }).subscribe({
        next: () => {
          this.matSnackBar.open("Notification settings updated", "Close", {duration: 3000});
        },
        error: () => {
          this.matSnackBar.open("Error while updating notification settings", "Close", {duration: 5000});
        }
      }
    );
  }
}
