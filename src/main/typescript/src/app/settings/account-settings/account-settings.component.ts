import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../../service/customer.service";
import {FormControl, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material/core";
import {AuthService} from "../../service/auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-account-settings',
  templateUrl: './account-settings.component.html',
  styleUrls: ['./account-settings.component.css']
})
export class AccountSettingsComponent implements OnInit {
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordFormControl = new FormControl('', [Validators.required]);
  matcher = new ErrorStateMatcher();

  constructor(private customerService: CustomerService, private authService: AuthService, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.customerService.getCurrentCustomer().subscribe(customer => {
      this.emailFormControl.setValue((customer.body as any).email);
    });
  }

  updateEmail() {
    if (this.emailFormControl.invalid || this.passwordFormControl.invalid) {
      return;
    }

    this.customerService.updateEmail({
      email: this.emailFormControl.getRawValue(),
      password: this.passwordFormControl.getRawValue()
    }).subscribe({
      next: (response) => {
        this.authService.storeToken((response.body as any).jwt);
        this.snackBar.open("Email updated successfully", "Close", {duration: 2000, panelClass: ["snackbar-success"]});
      },
      error: (error) => {
        if (error.status === 409) {
          this.snackBar.open("Email already exists", "Close", {duration: 5000, panelClass: ["snackbar-error"]});
        } else {
          this.snackBar.open("Password was not correct", "Close", {duration: 5000, panelClass: ["snackbar-error"]});
        }
      }
    });
  }
}
