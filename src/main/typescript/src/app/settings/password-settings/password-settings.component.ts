import { Component } from '@angular/core';
import {CustomerService} from "../../service/customer.service";

@Component({
  selector: 'app-password-settings',
  templateUrl: './password-settings.component.html',
  styleUrls: ['./password-settings.component.css']
})
export class PasswordSettingsComponent {
  oldPassword: string;
  newPassword: string;
  newPasswordRepeat: string;

  constructor(private customerService: CustomerService) { }
}
