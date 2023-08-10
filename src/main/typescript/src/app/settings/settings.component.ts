import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent {
  constructor(private router: Router) {}

  openPasswordSettings() {
    this.router.navigate(['/settings/password']);
  }

  openAccountSettings() {
    this.router.navigate(['/settings/account']);
  }

  openNotificationsSettings() {
    this.router.navigate(['/settings/notifications']);
  }
}
